package com.creative.contentservice.service;

import com.creative.contentservice.entity.AdContent;

import java.util.List;

public interface AdContentService {

    List<AdContent> getAllAdContents();

    AdContent getAdContentById(String adContentId);

}