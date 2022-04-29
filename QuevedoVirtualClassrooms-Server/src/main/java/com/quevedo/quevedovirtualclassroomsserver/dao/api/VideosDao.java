package com.quevedo.quevedovirtualclassroomsserver.dao.api;

import com.quevedo.quevedovirtualclassroomsserver.models.VideoInfoDTO;
import io.vavr.control.Either;

import java.io.InputStream;
import java.util.List;

public interface VideosDao {
    Either<String, List<VideoInfoDTO>> getAllVideos();
    Either<String, String> uploadVideo(VideoInfoDTO videoInfo);
    Either<String, String> deleteVideo(String videoId);
}
