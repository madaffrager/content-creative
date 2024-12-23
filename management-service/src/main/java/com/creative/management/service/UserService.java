package com.creative.management.service;

import com.creative.management.entity.AdContent;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    List<AdContent> getAllAdvertisements();

    ResponseEntity<AdContent> getAdvertisementById(String advertisementId);
}