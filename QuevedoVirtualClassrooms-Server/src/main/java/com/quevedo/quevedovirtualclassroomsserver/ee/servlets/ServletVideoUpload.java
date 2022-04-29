package com.quevedo.quevedovirtualclassroomsserver.ee.servlets;

import com.google.gson.Gson;
import com.quevedo.quevedovirtualclassroomsserver.common.config.Config;
import com.quevedo.quevedovirtualclassroomsserver.models.VideoInfoDTO;
import com.quevedo.quevedovirtualclassroomsserver.services.VideosServices;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@MultipartConfig
@WebServlet(name = "ServletVideoUpload2", value = "/videos/upload", loadOnStartup = 1)

public class ServletVideoUpload extends HttpServlet {
    private final Config config;
    private final VideosServices videosServices;
    private final Gson gson;

    @Inject
    public ServletVideoUpload(Config config, VideosServices videosServices, Gson gson){
        this.config = config;
        this.videosServices = videosServices;
        this.gson = gson;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File upload = new File(config.getUploadPath());

        String multipartContentType = "multipart/form-data";
        String fieldName = "multiPartServlet";
        Part filePart = request.getPart(fieldName);
        VideoInfoDTO videoInfo = new VideoInfoDTO();
        videoInfo.setName(filePart.getSubmittedFileName());
        videoInfo.setUploadTime(LocalDateTime.now());

        Map<Object, Object> responseData = null;
        final PrintWriter writer = response.getWriter();
        String linkName = null;

        try {
            if (request.getContentType() == null || !request.getContentType().toLowerCase(Locale.ROOT).contains(multipartContentType)){
                throw new Exception("Invalid contentType. It must be" + multipartContentType);
            }
            String type = filePart.getContentType();
            type = type.substring(type.lastIndexOf("/")+1);

            //Generate random name
            String extension = type;
            extension = (extension != null && !extension.equals("")) ? "." + extension : extension;
            String videoId = UUID.randomUUID().toString();
            String name = videoId + extension;
            videoInfo.setId(videoId);

            //Get absolute path of server
            String absoluteServerPath = upload + name;

            //Create link
            String path = request.getHeader("referer");
            linkName = path + "videos/" + name;
            videoInfo.setUrl(linkName);

            //Validate file
            String mimeType = filePart.getContentType();
            String[] allowedExts = new String[] {
                    "mp4"
            };
            String[] allowedMimeTypes = new String[] {
                    "video/mp4"
            };
            //Validate the file
            if (!ArrayUtils.contains(allowedExts, FilenameUtils.getExtension(absoluteServerPath)) ||
                    !ArrayUtils.contains(allowedMimeTypes, mimeType.toLowerCase())) {

                // Delete the uploaded file.
                File file = new File(absoluteServerPath);

                if (file.exists()) {
                    file.delete();
                }

                throw new Exception("File does not meet the validation.");
            }
            //Save the file on the disk

            File file = new File(upload, name);
            try(InputStream input = filePart.getInputStream()){
                Files.copy(input, file.toPath());
                videosServices.uploadVideo(videoInfo);
            }catch (Exception e){
                writer.println("<br/> ERROR: " + e);
            }
        }catch (Exception e){
            e.printStackTrace();
            writer.println("You either did not specify a file to upload or are " +
                    "trying to upload a file to a protected or nonexistent " +
                    "location.");
            writer.println("<br/> ERROR: " + e.getMessage());
            responseData = new HashMap<>();
            responseData.put("error", e.toString());
        }finally {
            String jsonResponseData;
            // Send response data.
            if (responseData != null){
                jsonResponseData = gson.toJson(responseData);
            }else {
                jsonResponseData = gson.toJson(videoInfo);
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponseData);
        }
    }
}
