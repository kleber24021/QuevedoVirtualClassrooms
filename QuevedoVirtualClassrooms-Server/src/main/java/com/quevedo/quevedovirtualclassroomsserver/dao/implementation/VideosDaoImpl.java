package com.quevedo.quevedovirtualclassroomsserver.dao.implementation;

import com.quevedo.quevedovirtualclassroomsserver.dao.api.VideosDao;
import com.quevedo.quevedovirtualclassroomsserver.models.VideoInfoDTO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Log4j2
public class VideosDaoImpl implements VideosDao {

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public VideosDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Either<String, List<VideoInfoDTO>> getAllVideos() {
        Either<String, List<VideoInfoDTO>> result;
        try {
            result = Either.right(jdbcTemplate.query("select * from VIDEOS", new BeanPropertyRowMapper<>(VideoInfoDTO.class)));
        }catch (DataAccessException dataAccessException){
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left("Exception found: " + dataAccessException.getMessage());
        }
        return result;
    }

    @Override
    public Either<String, String> uploadVideo(VideoInfoDTO videoInfo) {
        // TODO: 27/04/2022 Bring all the logic of writing video in disk to dao
        // This DAO is in charge of saving the video info at the Database
        Either<String, String> result;
        try {
            int updateResult = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "insert into VIDEOS (id, url, name, uploadTime) values (?, ?, ?, ?)");
                ps.setString(1, videoInfo.getId());
                ps.setString(2, videoInfo.getUrl());
                ps.setString(3, videoInfo.getName());
                ps.setTimestamp(4, Timestamp.valueOf(videoInfo.getUploadTime()));
                return ps;
            });
            if (updateResult > 0){
                result = Either.right("Object Created");
            }else {
                result = Either.right("No rows were affected");
            }
        }catch (DataAccessException dataAccessException){
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left("Object could not be created: " + dataAccessException.getMessage());
        }
        return result;
    }

    @Override
    public Either<String, String> deleteVideo(String videoId) {
        // TODO: 27/04/2022 Add delete video
        return Either.left("Not implemented");
    }
}
