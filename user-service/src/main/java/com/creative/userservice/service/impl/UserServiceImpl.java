package com.creative.userservice.service.impl;

import com.creative.userservice.converter.UserMapper;
import com.creative.userservice.dto.KeycloakUser;
import com.creative.userservice.dto.SignUpRequest;
import com.creative.userservice.entity.User;
import com.creative.userservice.repository.UserRepository;
import com.creative.userservice.service.KeyCloakService;
import com.creative.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final KeyCloakService keycloakService;
    @Override
    public String signUpUser(SignUpRequest signUpRequest) {
        LOGGER.info("UserServiceImpl | signUpUser is started");
        KeycloakUser keycloakUser = new KeycloakUser();
        keycloakUser.setUsername(signUpRequest.getUsername());
        keycloakUser.setPassword(signUpRequest.getPassword());
        keycloakUser.setEmail(signUpRequest.getEmail());
        keycloakUser.setFirstName(signUpRequest.getName());
        keycloakUser.setLastName(signUpRequest.getSurname());
        keycloakUser.setRole(signUpRequest.getRole());
        int status = keycloakService.createUserWithKeycloak(keycloakUser);
        if(status == 201) {
            LOGGER.info("UserServiceImpl | signUpUser | status : " + status);
            User signUpUser = UserMapper.signUpRequestToUser(signUpRequest);
            signUpUser.setCreatedAt(LocalDateTime.now());
            userRepository.save(signUpUser);
            return "Sign Up completed";
        }
        return "Not Registered";

    }
}
