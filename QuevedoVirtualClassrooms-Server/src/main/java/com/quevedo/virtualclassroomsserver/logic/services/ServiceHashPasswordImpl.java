package com.quevedo.virtualclassroomsserver.logic.services;

import com.quevedo.virtualclassroomsserver.facade.services.ServiceHashPassword;
import com.quevedo.virtualclassroomsserver.logic.services.utils.ServicesConsts;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

import java.util.HashMap;
import java.util.Map;

public class ServiceHashPasswordImpl implements ServiceHashPassword {
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    private ServiceHashPasswordImpl(Pbkdf2PasswordHash passwordHash){
        this.passwordHash = passwordHash;
    }

    @Override
    public String hash(String password) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(ServicesConsts.PBKDF_2_PASSWORD_HASH_ITERATIONS, ServicesConsts.PBKDF_2_PASSWORD_HASH_ITERATIONS_VALUE);
        parameters.put(ServicesConsts.PBKDF_2_PASSWORD_HASH_ALGORITHM, ServicesConsts.PBKDF_2_PASSWORD_HASH_ALGORITHM_VALUE);
        parameters.put(ServicesConsts.PBKDF_2_PASSWORD_HASH_SALT_SIZE_BYTES, ServicesConsts.PBKDF_2_PASSWORD_HASH_SALT_SIZE_BYTES_VALUE);
        parameters.put(ServicesConsts.PBKDF_2_PASSWORD_HASH_KEY_SIZE_BYTES, ServicesConsts.PBKDF_2_PASSWORD_HASH_KEY_SIZE_BYTES_VALUE);
        passwordHash.initialize(parameters);
        return passwordHash.generate(password.toCharArray());
    }

    @Override
    public boolean verify(String referenceHash, String inputHash) {
        return passwordHash.verify(inputHash.toCharArray(), referenceHash);
    }
}
