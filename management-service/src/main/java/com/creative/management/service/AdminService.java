package com.creative.management.service;

import com.creative.management.dto.AdContentRequest;
import com.creative.management.entity.AdContent;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {

    ResponseEntity<?> createAdvertisement(AdContentRequest advertisementRequest, String userId);

    ResponseEntity<?> updateAdvertisement(AdContentRequest advertisementRequest, String advertisementId);

    ResponseEntity<?> deleteAdvertisement(String advertisementId);

    List<AdContent> getAllAdvertisements();

    ResponseEntity<AdContent> getAdvertisementById(String advertisementId);

    ResponseEntity<?> approveAdvertisement(String advertisementId);

    ResponseEntity<?> rejectAdvertisement(String advertisementId);
}