package com.quevedo.virtualclassroomsserver.logic.ee.servlets;

import com.google.gson.Gson;
import com.quevedo.virtualclassroomsserver.common.models.common.ResourceType;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourcePostDTO;
import com.quevedo.virtualclassroomsserver.facade.services.ResourceServices;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
@MultipartConfig
@WebServlet(name = "ServletResourceUpload", value = "/virtual-classrooms/resources", loadOnStartup = 1)
public class ServletResourceUpload extends HttpServlet {
    private final ResourceServices resourceServices;
    private final Gson gson;

    @Inject
    public ServletResourceUpload(ResourceServices resourceServices, Gson gson) {
        this.resourceServices = resourceServices;
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String multipartContentType = "multipart/form-data";
        String fieldName = "resourceFile";
        Part filePart = request.getPart(fieldName);
        AtomicReference<String> jsonResponseData = new AtomicReference<>();
        try {
            if (request.getContentType() == null || !request.getContentType().toLowerCase(Locale.ROOT).contains(multipartContentType)) {
                throw new Exception("Invalid content type. It must be " + multipartContentType);
            }
            String type = filePart.getContentType();
            type = type.substring(type.lastIndexOf("/") + 1);
            String extension = type;
            extension = (extension != null && !extension.equals("")) ? "." + extension : extension;

            String mimeType = filePart.getContentType();
            String[] allowedMimeTypes = new String[]{
                    "video/mp4",
                    "image/jpg",
                    "image/png"
            };
            if (!ArrayUtils.contains(allowedMimeTypes, mimeType.toLowerCase())) {
                throw new Exception("Invalid file extension");
            }
            ResourcePostDTO resourcePostDTO = new ResourcePostDTO();
            resourcePostDTO.setResourceName(request.getParameter("resourceName"));
            resourcePostDTO.setTimestamp(LocalDateTime.now());
            resourcePostDTO.setClassroomUUID(request.getParameter("classroomUUID"));
            resourcePostDTO.setFileExtension(extension);
            resourcePostDTO.setFileStream(filePart.getInputStream());
            resourcePostDTO.setResourceType(ResourceType.getTypeByString(request.getParameter("resourceType")));

            resourceServices.uploadResource(resourcePostDTO)
                    .peek(result -> jsonResponseData.set(gson.toJson(result)))
                    .peekLeft(error -> jsonResponseData.set(gson.toJson(error)));
        } catch (Exception e) {
            jsonResponseData.set(e.getMessage());
        } finally {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponseData.get());
        }
    }
}
