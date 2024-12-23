package com.creative.management.service.impl;

import com.creative.management.dto.AdContentRequest;
import com.creative.management.entity.AdContent;
import com.creative.management.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final String BASE_URL = "http://ADVERTISEMENT-SERVICE:9001/api/v1/admin_role";
    private static final String USER_BASE_URL = "http://USER-SERVICE:9000/api/v1/users";
    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<?> createAdvertisement(AdContentRequest advertisementRequest, String userId) {
        String result = getRoleInfo();

        LOGGER.info("AdminServiceImpl | createAdvertisement | role result : " + result);

        if(result.equals("ROLE_ADMIN")){
            return restTemplate.postForEntity(
                    BASE_URL + "/create/{userId}",
                    advertisementRequest,
                    String.class,
                    userId
            );
        }

        return null;
    }

    @Override
    public ResponseEntity<?> updateAdvertisement(AdContentRequest advertisementRequest, String advertisementId) {
        String result = getRoleInfo();

        LOGGER.info("AdminServiceImpl | updateAdvertisement | role result : " + result);

        if(result.equals("ROLE_ADMIN")){
            /*Map< String, String > params = new HashMap< >();
            params.put("id", advertisementId);
            restTemplate.put(BASE_URL + "/update/"+ advertisementId, advertisementRequest, params);
            return new ResponseEntity("Advertisement Updated with id " + advertisementId, HttpStatus.OK);*/

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity <AdContentRequest> entity = new HttpEntity<>(advertisementRequest, httpHeaders);
            return restTemplate.exchange(BASE_URL + "/update/"+ advertisementId, HttpMethod.PUT, entity, String.class);
        }

        return null;    }

    @Override
    public ResponseEntity<?> deleteAdvertisement(String advertisementId) {
        String result = getRoleInfo();
        LOGGER.info("AdminServiceImpl | deleteAdvertisement | role result : " + result);

        if(result.equals("ROLE_ADMIN")){
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity < String > entity = new HttpEntity < > (httpHeaders);
            return restTemplate.exchange(BASE_URL + "/delete/" + advertisementId, HttpMethod.DELETE, entity, String.class);
        }
        return null;
    }

    @Override
    public List<AdContent> getAllAdvertisements() {
        String result = getRoleInfo();

        LOGGER.info("AdminServiceImpl | getAllAdvertisements | role result : " + result);
        if(result.equals("ROLE_ADMIN")){
            ResponseEntity<AdContent[]> restExchange =
                    restTemplate.getForEntity(
                            BASE_URL + "/alladvertisements",
                            AdContent[].class
                    );

            return Arrays.asList(restExchange.getBody());
        }
        return null;
    }

    @Override
    public ResponseEntity<AdContent> getAdvertisementById(String advertisementId) {

        String result = getRoleInfo();
        LOGGER.info("AdminServiceImpl | getAdvertisementById | role result : " + result);

        if(result.equals("ROLE_ADMIN")){
            return restTemplate.getForEntity(
                    BASE_URL + "/advertisement/{advertisementId}",
                    AdContent.class,
                    advertisementId
            );
        }
        return null;
    }

    @Override
    public ResponseEntity<?> approveAdvertisement(String advertisementId) {

        String result = getRoleInfo();

        LOGGER.info("AdminServiceImpl | approveAdvertisement | role result : " + result);

        if(result.equals("ROLE_ADMIN")){
            return restTemplate.getForEntity(
                    BASE_URL + "/advertisement/{advertisementId}/approve",
                    String.class,
                    advertisementId
            );
        }
        return null;
    }

    @Override
    public ResponseEntity<?> rejectAdvertisement(String advertisementId) {

        String result = getRoleInfo();

        LOGGER.info("AdminServiceImpl | approveAdvertisement | role result : " + result);

        if(result.equals("ROLE_ADMIN")){
            return restTemplate.getForEntity(
                    BASE_URL + "/advertisement/{advertisementId}/reject",
                    String.class,
                    advertisementId
            );
        }

        return null;
    }

    private String getRoleInfo(){

        LOGGER.info("AdminServiceImpl | getRoleInfo is started");

        String result = this.restTemplate.getForObject(USER_BASE_URL + "/info",String.class);

        LOGGER.info("AdminServiceImpl | getRoleInfo | role result : " + result);

        return result;
    }
}
