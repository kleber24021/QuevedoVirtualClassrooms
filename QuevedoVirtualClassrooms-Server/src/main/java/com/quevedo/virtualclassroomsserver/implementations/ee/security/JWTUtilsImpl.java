package com.quevedo.virtualclassroomsserver.implementations.ee.security;

import com.quevedo.virtualclassroomsserver.facade.ee.security.JWTUtils;
import com.quevedo.virtualclassroomsserver.implementations.ee.EEConst;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JWTUtilsImpl implements JWTUtils {

    private final Key generatedKey;

    @Inject
    public JWTUtilsImpl(@Named(EEConst.JWT) Key generatedKey){
        this.generatedKey = generatedKey;
    }

    @Override
    public String getOK2(String username, String group) throws NoSuchAlgorithmException {
        return Jwts.builder()
                .setSubject(EEConst.JWT_SUBJECT)
                .setIssuer(EEConst.JWT_ISSUER)
                .setExpiration(Date
                        .from(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault())
                                .toInstant()))
                .claim(EEConst.USER, username)
                .claim(EEConst.GROUP, group)
                .signWith(generatedKey).compact();
    }

    @Override
    public Jws<Claims> verifyToken(String token) throws NoSuchAlgorithmException {
        return Jwts.parserBuilder()
                .setSigningKey(generatedKey)
                .build()
                .parseClaimsJws(token);
    }
}
