package com.creative.contentservice.service;

import com.creative.contentservice.dto.AdContentRequest;
import com.creative.contentservice.entity.AdContent;

import java.util.List;

public interface AdminService {

    void createAdContent(AdContentRequest adContentRequest, String userId);

    void updateAdContent(AdContentRequest adContentRequest, String adContentId);

    void deleteAdContent(String adContentId);

    AdContent approveAdContent(String adContentId);

    AdContent rejectAdContent(String adContentId);

    List<AdContent> getAllAdContentsForAdmin();

    AdContent getAdContentByIdForAdmin(String adContentId);

}
