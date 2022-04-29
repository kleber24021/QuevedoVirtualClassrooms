package com.quevedo.quevedovirtualclassroomsserver.ee.rest;

import com.quevedo.quevedovirtualclassroomsserver.models.VideoInfoDTO;
import com.quevedo.quevedovirtualclassroomsserver.services.VideosServices;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/videos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class VideoRest {
    private final VideosServices videosServices;

    @Inject
    public VideoRest(VideosServices videosServices){
        this.videosServices = videosServices;
    }

    @GET
    public Response getAllVideos() {
        Either<String, List<VideoInfoDTO>> result = videosServices.getAllVideos();
        Response response;
        if (result.isRight()){
            response = Response.ok(result.get()).build();
        }else{
            response = Response.status(Response.Status.BAD_REQUEST).entity(result.getLeft()).build();
        }
        return response;
    }
}