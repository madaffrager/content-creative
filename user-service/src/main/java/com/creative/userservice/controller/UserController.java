package com.creative.userservice.controller;

import com.creative.userservice.dto.LoginRequest;
import com.creative.userservice.dto.SignUpRequest;
import com.creative.userservice.service.KeyCloakService;
import com.creative.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final UserService userService;
    private final KeyCloakService keyCloakService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody SignUpRequest signUpRequest) {
        LOGGER.info("UserController | signUpUser is started");
        LOGGER.info("UserController | signUpUser | SignUpRequest role : " + signUpRequest.getRole());
        LOGGER.info("UserController | signUpUser | SignUpRequest email : " + signUpRequest.getEmail());
        LOGGER.info("UserController | signUpUser | SignUpRequest name : " + signUpRequest.getName());
        return ResponseEntity.ok(userService.signUpUser(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest request){

        LOGGER.info("UserController | login is started");

        AccessTokenResponse accessTokenResponse =keyCloakService.loginWithKeycloak(request);
        if (accessTokenResponse == null){
            LOGGER.info("UserController | login | Http Status Bad Request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(accessTokenResponse);
        }
        LOGGER.info("UserController | login | Http Status Ok");

        return ResponseEntity.ok(accessTokenResponse);
    }

    @GetMapping("/info")
    public ResponseEntity<String> getUserInfo(){
        LOGGER.info("UserController | getUserInfo is started");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("UserController | infoUser | auth toString : " + auth.toString());
        LOGGER.info("UserController | infoUser | auth getPrincipal : " + auth.getPrincipal());
        KeycloakPrincipal principal = (KeycloakPrincipal) auth.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();

        String username = accessToken.getPreferredUsername();
        String email = accessToken.getEmail();
        String lastname = accessToken.getFamilyName();
        String firstname = accessToken.getGivenName();
        String realmName = accessToken.getIssuer();
        AccessToken.Access access = accessToken.getRealmAccess();
        Set<String> roles = access.getRoles();
        String role = roles.stream()
                .filter(s -> s.equals("ROLE_USER") || s.equals("ROLE_ADMIN"))
                .findAny()
                .orElse("noElement");
        return ResponseEntity.ok(role);
    }
}
