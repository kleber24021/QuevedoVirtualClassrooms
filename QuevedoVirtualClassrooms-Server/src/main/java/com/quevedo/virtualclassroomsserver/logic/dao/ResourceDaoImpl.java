package com.quevedo.virtualclassroomsserver.logic.dao;

import com.quevedo.virtualclassroomsserver.common.config.Config;
import com.quevedo.virtualclassroomsserver.common.config.QueriesLoader;
import com.quevedo.virtualclassroomsserver.common.models.common.ResourceType;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourceDetailGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourceLiteGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourcePostDTO;
import com.quevedo.virtualclassroomsserver.common.models.server.resource.Resource;
import com.quevedo.virtualclassroomsserver.common.models.server.resource.ResourceComment;
import com.quevedo.virtualclassroomsserver.facade.dao.ResourceDao;
import com.quevedo.virtualclassroomsserver.logic.dao.utils.rowmappers.CommentRowMapper;
import com.quevedo.virtualclassroomsserver.logic.dao.utils.rowmappers.ResourceRowMapper;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
public class ResourceDaoImpl implements ResourceDao {
    private final JdbcTemplate template;
    private final Config config;
    private final QueriesLoader queriesLoader;
    private final ResourceRowMapper resourceRowMapper;
    private final CommentRowMapper commentRowMapper;

    @Inject
    public ResourceDaoImpl(JdbcTemplate template,
                           Config config,
                           QueriesLoader queriesLoader,
                           ResourceRowMapper resourceRowMapper,
                           CommentRowMapper commentRowMapper) {
        this.commentRowMapper = commentRowMapper;
        this.resourceRowMapper = resourceRowMapper;
        this.template = template;
        this.config = config;
        this.queriesLoader = queriesLoader;
    }


    @Override
    public Either<String, ResourceLiteGetDTO> uploadResource(Resource toUpload, InputStream toSaveInDisk, String fileExtension) {
        Either<String, ResourceLiteGetDTO> result = null;
//        1. Copiar el inputStream en el directorio del config
//        1.1 Crear la ruta para guardar el archivo
        String savePath = toUpload.getResourceType().equals(ResourceType.IMAGE) ? "/IMAGES" : "/VIDEOS";
        File upload = new File(config.getUploadPath() + savePath);
        //Check directory and create it
        if (!upload.exists()) {
            upload.mkdirs();
        }
        String fileName = toUpload.getUuidResource().toString() + fileExtension;
        upload = new File(upload, fileName);
        Path toPath = Paths.get(upload.getPath());
        Path parent = toPath.getParent();
        try {
            if (parent != null) {
                if (Files.notExists(parent)) {
                    Files.createDirectories(parent);
                }
            }
        } catch (IOException ioException) {
            log.error(ioException);
            return Either.left("UNEXPECTED ERROR: " + ioException);
        }
//          1.2 Copiamos el archivo
        try (InputStream input = toSaveInDisk) {
            Files.copy(input, upload.toPath());
        } catch (Exception e) {
            result = Either.left("UNEXPECTED ERROR: " + e);
        }
        if (result == null) {
            //        2. Coger URL del archivo creado
            toUpload.setResourceEndpoint(fileName);
//        3. Subir información del archivo a la BBDD
            try {
                int rows = template.update(conn -> {
                    PreparedStatement ps = conn.prepareStatement(queriesLoader.getInsertResource());
                    ps.setString(1, toUpload.getUuidResource().toString());
                    ps.setString(2, toUpload.getResourceName());
                    ps.setString(3, toUpload.getResourceEndpoint());
                    ps.setTimestamp(4, Timestamp.valueOf(toUpload.getTimestamp()));
                    ps.setString(5, toUpload.getClassroomUUID().toString());
                    ps.setInt(6, toUpload.getResourceType().getValue());
                    return ps;
                });
                if (rows > 0) {
                    result = Either.right(toUpload.toLiteDTO());
                } else {
                    result = Either.left("UNEXPECTED ERROR: " + " the resource could not be save at the database");
                }
            } catch (DataAccessException dataAccessException) {
                log.error(dataAccessException.getMessage(), dataAccessException);
                result = Either.left("UNEXPECTED ERROR: " + dataAccessException);
            }
//        4. Devolver la información al rest
        }
        return result;
    }

    @Override
    public Either<String, Integer> editResource(ResourcePostDTO toEdit) {
        //TODO: verificar si queremos editar recursos y crear modelo para hacerlo
        Either<String, Integer> result;
        try {
            result = Either.right(template.update(queriesLoader.getUpdateResource(), toEdit.getResourceName(), toEdit.getClassroomUUID(), ""));
        }catch (DataAccessException dataAccessException){
            result = Either.left("ERROR: " + dataAccessException);
        }
        return result;
    }

    @Override
    public Either<String, List<ResourceLiteGetDTO>> getAllResourcesOfClassroom(String classroomId, ResourceType resourceType) {
        Either<String, List<ResourceLiteGetDTO>> result;
        try {
            result = Either.right(template.query(queriesLoader.getGetAllResourcesByClassroomId(), resourceRowMapper, classroomId, resourceType.getValue()).stream().map(Resource::toLiteDTO).collect(Collectors.toList()));
        } catch (DataAccessException dataAccessException) {
            result = Either.left("ERROR: " + dataAccessException);
        }
        return result;
    }

    @Override
    public Either<String, ResourceDetailGetDTO> getDetailResource(String resourceUUID) {
        Either<String, ResourceDetailGetDTO> result;
        try {
            Resource resource = template.queryForObject(queriesLoader.getGetResourceById(), resourceRowMapper, resourceUUID);
            Objects.requireNonNull(resource).setComments(prepareList(template.query(queriesLoader.getGetAllCommentsByResource(), commentRowMapper, resourceUUID)));
            result = Either.right(resource.toDetailDTO());

        } catch (DataAccessException dataAccessException) {
            result = Either.left("DATA ACCESS: " + dataAccessException);
        } catch (NullPointerException nullPointerException) {
            result = Either.left("NULL POINTER: " + nullPointerException);
        }
        return result;
    }

    @Override
    public Either<String, File> getResourceFile(String fileName) {
        Either<String, File> result;
        String resourceUUID = fileName.split("\\.")[0];
        try {
            File file;
            Resource resource = template.queryForObject(queriesLoader.getGetResourceById(), resourceRowMapper, resourceUUID);
            switch (Objects.requireNonNull(resource).getResourceType()) {
                case VIDEO:
                    file = new File(config.getUploadPath() + "/VIDEOS/" + fileName);
                    break;
                case IMAGE:
                    file = new File(config.getUploadPath() + "/IMAGES/" + fileName);
                    break;
                default:
                    return Either.left("Not supported resource, yet...");
            }
            if (file.exists()) {
                result = Either.right(file);
            } else {
                result = Either.left("The file of this resource is not at the directory, contact admin");
            }
        } catch (IncorrectResultSizeDataAccessException sizeException) {
            result = Either.left("This resource does not exist");
        }
        return result;
    }

    @Override
    public String deleteResource(String resourceUUID) {
        StringBuilder errorResult = new StringBuilder();
        try {
            Resource resource = template.queryForObject(queriesLoader.getGetResourceById(), resourceRowMapper, resourceUUID);
            File file;
            switch (Objects.requireNonNull(resource).getResourceType()) {
                case VIDEO:
                    file = new File(config.getUploadPath() + "/VIDEOS/" + resource.getResourceEndpoint());
                    break;
                case IMAGE:
                    file = new File(config.getUploadPath() + "/IMAGES/" + resource.getResourceEndpoint());
                    break;
                default:
                    return "Not supported resource, yet...";
            }
            if (template.update(queriesLoader.getDeleteResourceById(), resourceUUID) > 0){
                errorResult.append(" Deleted resource");
            }else {
                errorResult.append(" The record of this resource could not be deleted");
            }
            if (file.exists()){
                if (file.delete()){
                    errorResult.append("Deleted File");
                }else {
                    errorResult.append("The file of this resource exists but could not be deleted");
                }
            }else {
                errorResult.append("The file of this resource could not be found.");
            }
            return errorResult.toString();
        }catch (DataAccessException dataAccessException){
            errorResult = new StringBuilder("ERROR: " + dataAccessException);
        }
        return errorResult.toString();
    }

    private List<ResourceComment> prepareList(List<ResourceComment> input) {
        List<ResourceComment> notAnswers = input.stream().filter(comment -> !comment.isAnswer()).collect(Collectors.toList());
        List<ResourceComment> answers = input.stream().filter(ResourceComment::isAnswer).collect(Collectors.toList());

        return notAnswers.stream()
                .map(comment -> {
                    answers.forEach(answer -> {
                        if (comment.getUuidComment().toString().equals(answer.getAnswersTo().toString())) {
                            comment.pushCommentToAnswers(answer);
                        }
                    });
                    return comment;
                }).collect(Collectors.toList());
    }
}
