package com.quevedo.virtualclassroomsserver.implementations.ee.security;

import com.quevedo.virtualclassroomsserver.common.models.common.UserType;
import com.quevedo.virtualclassroomsserver.facade.services.UserServices;
import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import lombok.NoArgsConstructor;

import java.util.Collections;

@ApplicationScoped
@NoArgsConstructor
public class InMemoryIdentityStore implements IdentityStore {
    private UserServices userServices;

    @Inject
    public InMemoryIdentityStore(UserServices userServices){
        this.userServices = userServices;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
        if (credential instanceof UsernamePasswordCredential){
            UsernamePasswordCredential user = (UsernamePasswordCredential) credential;
            Either<String, UserType> tipoUsuario = userServices.checkLogin(user.getCaller(), user.getPasswordAsString());
            if (tipoUsuario.isRight() && tipoUsuario.get() != null){
                result = new CredentialValidationResult(user.getCaller(), Collections.singleton(tipoUsuario.get().toString()));
            }
        }
        return result;
    }

    @Override
    public int priority() {
        return 10;
    }
}
