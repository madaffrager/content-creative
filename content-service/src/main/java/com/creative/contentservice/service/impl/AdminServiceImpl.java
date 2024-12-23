package com.creative.contentservice.service.impl;

import com.creative.contentservice.converter.AdContentMapper;
import com.creative.contentservice.dto.AdContentRequest;
import com.creative.contentservice.entity.AdContent;
import com.creative.contentservice.entity.AdvertisementState;
import com.creative.contentservice.repository.AdContentRepository;
import com.creative.contentservice.service.AdminService;
import com.creative.contentservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final AdContentRepository advertisementRepository;
    private final MessageService messagingService;

    @Override
    public void createAdContent(AdContentRequest adContentRequest, String userId) {

        LOGGER.info("AdminServiceImpl | createAdvertisement | userId : " + userId);

        AdContent createdAdvertisement = AdContentMapper.adContentRequestToAdContent(adContentRequest);
        createdAdvertisement.setUserId(Long.valueOf(userId));
        createdAdvertisement.setViewCount(1L);
        createdAdvertisement.setState(AdvertisementState.WAITING);

        LOGGER.info("AdminServiceImpl | createAdvertisement | createdAdvertisement state : " + createdAdvertisement.getState().toString());

        advertisementRepository.save(createdAdvertisement);

    }

    @Override
    public void updateAdContent(AdContentRequest adContentRequest, String adContentId) {
        LOGGER.info("AdminServiceImpl | updateAdvertisement | advertisementId : " + adContentId);

        Optional<AdContent> optionalAdvertisement = advertisementRepository.findById(Long.valueOf(adContentId));

        AdContent updatedAdvertisement = optionalAdvertisement.get();

        LOGGER.info("AdminServiceImpl | updateAdvertisement | updatedAdvertisement title : " + updatedAdvertisement.getTitle());

        if (updatedAdvertisement.getPrice() != adContentRequest.getPrice()){
            updatedAdvertisement.setPrice(adContentRequest.getPrice());
        }
        if (updatedAdvertisement.getTitle() != adContentRequest.getTitle()){
            updatedAdvertisement.setTitle(adContentRequest.getTitle());
        }

        updatedAdvertisement.setState(AdvertisementState.WAITING);

        LOGGER.info("AdminServiceImpl | updateAdvertisement | updatedAdvertisement state : " + updatedAdvertisement.getState().toString());

        advertisementRepository.save(updatedAdvertisement);
    }

    @Override
    public void deleteAdContent(String adContentId) {
        LOGGER.info("AdminServiceImpl | deleteAdContent | adContentId : " + adContentId);

        Optional<AdContent> optionalAdvertisement = advertisementRepository.findById(Long.valueOf(adContentId));
        AdContent deletedAdvertisement = optionalAdvertisement.get();

        LOGGER.info("AdminServiceImpl | deleteAdContent | deletedAdvertisement title : " + deletedAdvertisement.getTitle());

        advertisementRepository.delete(deletedAdvertisement);
    }

    @Override
    public AdContent approveAdContent(String adContentId) {
        LOGGER.info("AdminServiceImpl | approveAdvertisement | advertisementId : " + adContentId);

        Optional<AdContent> optionalAdvertise = advertisementRepository.findById(Long.valueOf(adContentId));

        AdContent advertisement = optionalAdvertise.get();

        LOGGER.info("AdminServiceImpl | approveAdvertisement | advertisement title : " + advertisement.getTitle());

        advertisement.setState(AdvertisementState.APPROVED);

        // To access Advertisement ID , use saveAndFlush
        advertisementRepository.saveAndFlush(advertisement);

        messagingService.sendMessage(advertisement);

        return advertisement;
    }

    @Override
    public AdContent rejectAdContent(String adContentId) {
        LOGGER.info("AdminServiceImpl | rejectAdContent | adContentId : " + adContentId);

        Optional<AdContent> optionalAdvertise = advertisementRepository.findById(Long.valueOf(adContentId));

        AdContent advertisement = optionalAdvertise.get();

        LOGGER.info("AdminServiceImpl | approveAdvertisement | advertisement title : " + advertisement.getTitle());

        advertisement.setState(AdvertisementState.REJECTED);

        advertisementRepository.save(advertisement);

        return advertisement;
    }

    @Override
    public List<AdContent> getAllAdContentsForAdmin() {
        LOGGER.info("AdminServiceImpl | getAllAdvertisementsForAdmin is started");

        List<AdContent> advertiseList = advertisementRepository.findAll();

        LOGGER.info("AdminServiceImpl | getAllAdvertisements | advertiseList size : " + advertiseList.size());

        return advertiseList;
    }

    @Override
    public AdContent getAdContentByIdForAdmin(String adContentId) {
        LOGGER.info("AdvertisementServiceImpl | getAdvertisementById is started");

        Optional<AdContent> optionalAdvertisement = advertisementRepository.findById(Long.valueOf(adContentId));

        AdContent advertisement = optionalAdvertisement.get();

        LOGGER.info("AdvertisementServiceImpl | getAdvertisementById | advertisement title : " + advertisement.getTitle());

        return advertisement;    }
}
