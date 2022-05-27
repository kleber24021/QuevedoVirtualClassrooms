package com.quevedo.virtualclassroomsserver.facade.services;

public interface ServiceHashPassword {
    String hash(String password);
    boolean verify(String referenceHash, String inputHash);
}
