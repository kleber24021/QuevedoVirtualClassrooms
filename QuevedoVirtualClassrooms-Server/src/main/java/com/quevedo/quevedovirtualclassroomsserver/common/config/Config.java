package com.quevedo.quevedovirtualclassroomsserver.common.config;


import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Log4j2
@Getter
@Singleton
public class Config {
    private String uploadPath;
    private String dbPath;
    private String dbUser;
    private String dbPass;
    private String dbDriver;


    void loadConfig(InputStream fileStream){
        try {
            Yaml yaml = new Yaml();
            Map<String, String> map;
            map = (Map<String, String>) yaml.loadAll(fileStream).iterator().next();
            this.uploadPath = map.get("uploadPath");
            this.dbPath = map.get("path");
            this.dbUser = map.get("user");
            this.dbPass = map.get("password");
            this.dbDriver = map.get("driver");

        }catch (Exception exception){
            log.error(exception.getMessage(), exception);
        }
    }
}
