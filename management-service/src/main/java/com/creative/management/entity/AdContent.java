package com.creative.management.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AdContent implements Serializable {
    private Long id;
    private Long userId;
    private String title;
    private BigDecimal price;
    private Long viewCount;
    private AdvertisementState state;

}
