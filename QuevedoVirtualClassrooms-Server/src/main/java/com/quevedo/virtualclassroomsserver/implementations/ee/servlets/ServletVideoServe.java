package com.quevedo.virtualclassroomsserver.implementations.ee.servlets;

import com.google.gson.Gson;
import com.quevedo.virtualclassroomsserver.facade.services.ResourceServices;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

@WebServlet(name = "ServletVideoServe", value = "/virtual-classrooms/resources/file/*")
public class ServletVideoServe extends HttpServlet {
    private final ResourceServices resourceServices;
    private final Gson gson;

    @Inject
    public ServletVideoServe(ResourceServices resourceServices, Gson gson){
        this.resourceServices = resourceServices;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
        String extension = filename.split("\\.")[1];
        Either<String, File> result = resourceServices.getResourceFile(filename);
        if (result.isRight()){
            try {
                switch (extension){
                    case "mp4":
                        response.setContentType("video/mp4");
                        break;
                    case "jpg":
                        response.setContentType("image/jpg");
                        break;
                    case "png":
                        response.setContentType("image/png");
                        break;
                    default:
                        throw new Exception("Unsupported type exception");
                }
                response.setHeader("Content-Type", getServletContext().getMimeType(filename));
                response.setHeader("Content-Length", String.valueOf(result.get().length()));
                response.setHeader("Content-Disposition", "inline; filename=\"" + result.get().getName() + "\"");
                Files.copy(result.get().toPath(), response.getOutputStream());
            }catch (Exception e){
                response.setContentType("application/json");
                response.getWriter().write(gson.toJson("ERROR: " + e.getMessage()));
            }
        }else {
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(result.getLeft()));
        }
    }
}
