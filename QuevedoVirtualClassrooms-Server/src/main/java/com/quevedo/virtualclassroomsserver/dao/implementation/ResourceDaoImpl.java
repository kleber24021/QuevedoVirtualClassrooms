package com.quevedo.virtualclassroomsserver.dao.implementation;

import com.quevedo.virtualclassroomsserver.common.config.Config;
import com.quevedo.virtualclassroomsserver.common.config.QueriesLoader;
import com.quevedo.virtualclassroomsserver.common.models.common.ResourceType;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourceDetailGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourceLiteGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourcePostDTO;
import com.quevedo.virtualclassroomsserver.common.models.server.resource.Resource;
import com.quevedo.virtualclassroomsserver.dao.api.ResourceDao;
import com.quevedo.virtualclassroomsserver.dao.utils.rowmappers.ResourceRowMapper;
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

    @Inject
    public ResourceDaoImpl(JdbcTemplate template,
                           Config config,
                           QueriesLoader queriesLoader,
                           ResourceRowMapper resourceRowMapper) {
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
        if (!upload.exists()){
            upload.mkdirs();
        }
        String fileName = toUpload.getUuidResource().toString() + fileExtension;
        upload = new File(upload, fileName);
        Path toPath = Paths.get(upload.getPath());
        Path parent = toPath.getParent();
        try {
            if (parent != null){
                if (Files.notExists(parent)){
                    Files.createDirectories(parent);
                }
            }
        }catch (IOException ioException){
            return Either.left("UNEXPECTED ERROR: " + "The resource saving directory could not be created");
        }
//          1.2 Copiamos el archivo
        try (InputStream input = toSaveInDisk) {
            Files.copy(input, upload.toPath());
        } catch (Exception e) {
            result = Either.left("UNEXPECTED ERROR: " + "You either did not specify a file to upload or are " +
                    "trying to upload a file to a protected or nonexistent " +
                    "location.");
        }
        if (result == null) {
            //        2. Coger URL del archivo creado
            toUpload.setResourceUrl(toUpload.getResourceUrl() + "/file/" + fileName);
//        3. Subir información del archivo a la BBDD
            try {
                int rows = template.update(conn -> {
                    PreparedStatement ps = conn.prepareStatement(queriesLoader.getInsertResource());
                    ps.setString(1, toUpload.getUuidResource().toString());
                    ps.setString(2, toUpload.getResourceName());
                    ps.setString(3, toUpload.getResourceUrl());
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
                result = Either.left("UNEXPECTED ERROR: " + dataAccessException.getMessage());
            }
//        4. Devolver la información al rest
        }
        return result;
    }

    @Override
    public Either<String, ResourceLiteGetDTO> editResource(ResourcePostDTO toEdit) {
        return null;
    }

    @Override
    public Either<String, List<ResourceLiteGetDTO>> getAllResourcesOfClassroom(String classroomId) {
        Either<String, List<ResourceLiteGetDTO>> result;
        try {
            result = Either.right(template.query(queriesLoader.getGetAllResourcesByClassroomId(), resourceRowMapper, classroomId).stream().map(Resource::toLiteDTO).collect(Collectors.toList()));
        }catch (DataAccessException dataAccessException){
            result = Either.left("ERROR: " + dataAccessException);
        }
        return result;
    }

    @Override
    public Either<String, ResourceDetailGetDTO> getDetailResource(String resourceUUID) {
        Either<String, ResourceDetailGetDTO> result;
        // TODO: 13/05/2022 Implementar que traigar los comentarios tmb, dos llamadas a bbdd
        try {
            result = Either.right(template.queryForObject(queriesLoader.getGetResourceById(), resourceRowMapper, resourceUUID).toDetailDTO());
        }catch (DataAccessException dataAccessException){
            result = Either.left("ERROR: " + dataAccessException);
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
    public Either<String, Boolean> deleteResource(String resourceUUID) {
        return null;
    }
}
