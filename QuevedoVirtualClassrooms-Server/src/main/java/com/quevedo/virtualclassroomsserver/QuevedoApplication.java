package com.quevedo.virtualclassroomsserver;


import com.quevedo.virtualclassroomsserver.common.constants.RestConsts;
import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@ApplicationPath(RestConsts.APP_PATH)
@DeclareRoles({RestConsts.ROLE_STUDENT, RestConsts.ROLE_TEACHER})
public class QuevedoApplication extends Application {

}