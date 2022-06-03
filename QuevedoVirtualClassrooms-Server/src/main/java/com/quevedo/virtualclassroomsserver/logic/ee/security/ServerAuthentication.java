package com.quevedo.virtualclassroomsserver.logic.ee.security;

import com.quevedo.virtualclassroomsserver.facade.ee.security.JWTUtils;
import com.quevedo.virtualclassroomsserver.logic.ee.EEConst;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.SignatureException;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;

@Log4j2
public class ServerAuthentication implements HttpAuthenticationMechanism {
    private final InMemoryIdentityStore identityStore;
    private final JWTUtils jwtUtils;

    @Inject
    public ServerAuthentication(InMemoryIdentityStore identityStore, JWTUtils jwtUtils) {
        this.identityStore = identityStore;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                HttpMessageContext httpMessageContext) throws AuthenticationException {
        CredentialValidationResult validationResult = CredentialValidationResult.INVALID_RESULT;
        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String[] headerValues = header.split(" ");
            if (headerValues[0].equalsIgnoreCase(EEConst.BASIC)) {
                //Case of basic auth
                String userPass = new String(Base64.getUrlDecoder().decode(headerValues[1]));
                String[] splitUserPass = userPass.split(EEConst.USER_PASS_SPLIT);
                validationResult = identityStore.validate(new UsernamePasswordCredential(splitUserPass[0], splitUserPass[1]));
                if (validationResult.getStatus() == CredentialValidationResult.Status.VALID) {
                    try {
                        String newToken = jwtUtils.getOK2(splitUserPass[0], validationResult.getCallerGroups().stream().findFirst().get());
                        httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, newToken);
                    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                        log.error(noSuchAlgorithmException.getMessage(), noSuchAlgorithmException);
                    }
                }
            } else {
                //Case of bearer
                String token = headerValues[1];
                try {
                    Jws<Claims> tokenClaims = jwtUtils.verifyToken(token);
                    String username = tokenClaims.getBody().get(EEConst.USER).toString();
                    String rol = tokenClaims.getBody().get(EEConst.GROUP).toString();
                    validationResult = new CredentialValidationResult(username, Collections.singleton(rol));
                    String newToken = jwtUtils.getOK2(username, rol);
                    httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, newToken);
                } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    log.error(noSuchAlgorithmException.getMessage(), noSuchAlgorithmException);
                } catch (ExpiredJwtException expiredJwtException) {
                    try {
                        httpServletResponse.sendError(Response.Status.UNAUTHORIZED.getStatusCode(), EEConst.TOKEN_EXPIRADO);
                    } catch (IOException ioException) {
                        httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, EEConst.TOKEN_EXPIRADO);
                    }
                } catch (SignatureException signatureException) {
                    try {
                        httpServletResponse.sendError(Response.Status.UNAUTHORIZED.getStatusCode(), EEConst.TOKEN_INVALIDO);
                    } catch (IOException ioException) {
                        httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, EEConst.TOKEN_INVALIDO);
                    }
                }
            }
        }
        if (validationResult.getStatus().equals(CredentialValidationResult.Status.INVALID)){
            return httpMessageContext.doNothing();
        }
        return httpMessageContext.notifyContainerAboutLogin(validationResult);
    }
}
