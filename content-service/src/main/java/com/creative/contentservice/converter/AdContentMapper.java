package com.creative.contentservice.converter;

import com.creative.contentservice.dto.AdContentRequest;
import com.creative.contentservice.entity.AdContent;
import org.springframework.stereotype.Component;

@Component
public class AdContentMapper {
    public static AdContent adContentRequestToAdContent(AdContentRequest adContentRequest) {
        AdContent adContent = new AdContent();
        adContent.setPrice(adContentRequest.getPrice());
        adContent.setTitle(adContentRequest.getTitle());
        return adContent;
    }
}
