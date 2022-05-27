package com.quevedo.virtualclassroomsserver.implementations.ee.security;

import com.quevedo.virtualclassroomsserver.implementations.ee.EEConst;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.security.Key;

public class KeyGenerator {
    @Singleton
    @Produces
    @Named(EEConst.JWT)
    public Key key(){
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}
