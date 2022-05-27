package com.quevedo.virtualclassroomsserver.facade.ee.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.security.NoSuchAlgorithmException;

public interface JWTUtils {

    String getOK2(String username, String group) throws NoSuchAlgorithmException;

    Jws<Claims> verifyToken(String token) throws NoSuchAlgorithmException;

}
