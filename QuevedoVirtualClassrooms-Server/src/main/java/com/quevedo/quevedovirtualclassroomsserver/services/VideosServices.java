package com.quevedo.quevedovirtualclassroomsserver.services;

import com.quevedo.quevedovirtualclassroomsserver.dao.api.VideosDao;
import com.quevedo.quevedovirtualclassroomsserver.models.VideoInfoDTO;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class VideosServices {
    private final VideosDao videosDao;

    @Inject
    public VideosServices(VideosDao videosDao){
        this.videosDao = videosDao;
    }

    public Either<String, List<VideoInfoDTO>> getAllVideos(){
        return videosDao.getAllVideos();
    }

    public Either<String, String> uploadVideo(VideoInfoDTO videoInfo){
        return videosDao.uploadVideo(videoInfo);
    }

    public Either<String, String> deleteVideo(String videoId){
        return videosDao.deleteVideo(videoId);
    }
}
