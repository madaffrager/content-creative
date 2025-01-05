package com.creative.report.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdContentDto implements Serializable {
    private Long id;
    private String title;
    private Long viewCount;
}
