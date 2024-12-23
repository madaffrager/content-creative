package com.creative.contentservice.service.impl;

import com.creative.contentservice.entity.AdContent;
import com.creative.contentservice.entity.AdvertisementState;
import com.creative.contentservice.repository.AdContentRepository;
import com.creative.contentservice.service.AdContentService;
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

public class AdContentServiceImpl implements AdContentService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final AdContentRepository adContentRepository;

    @Override
    public List<AdContent> getAllAdContents() {
        LOGGER.info("AdContentServiceImpl | getAllAdContents is started");

        List<AdContent> adContents = adContentRepository.findAll();

        LOGGER.info("AdContentServiceImpl | getAllAdContents | adContents size : " + adContents.size());

        adContents.stream().filter(advertisement-> advertisement.getState() == AdvertisementState.APPROVED).forEach(
                advertisement -> advertisement.setViewCount(advertisement.getViewCount()+1));

        LOGGER.info("AdContentServiceImpl | getAllAdContents | adContents size : " + adContents.size());

        return adContents;
    }

    @Override
    public AdContent getAdContentById(String adContentId) {

        LOGGER.info("AdContentServiceImpl | getAdContentById is started");

        Optional<AdContent> optionalAdvertisement = adContentRepository.findById(Long.valueOf(adContentId));

        AdContent advertisement = optionalAdvertisement.get();

        LOGGER.info("AdContentServiceImpl | getAdContentById | advertisement title : " + advertisement.getTitle());

        advertisement.setViewCount(advertisement.getViewCount()+1);

        return advertisement;
    }
}
