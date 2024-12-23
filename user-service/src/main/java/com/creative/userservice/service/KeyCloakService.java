package com.creative.userservice.service;

import com.creative.userservice.dto.KeycloakUser;
import com.creative.userservice.dto.LoginRequest;
import org.keycloak.representations.AccessTokenResponse;

public interface KeyCloakService {
    public AccessTokenResponse loginWithKeycloak(LoginRequest request);
    public int createUserWithKeycloak(KeycloakUser keycloakUser);
}
