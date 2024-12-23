package com.creative.management.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdContentRequest {

    private String title;
    private BigDecimal price;
}