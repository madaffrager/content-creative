package com.creative.report.service.impl;

import com.creative.report.dto.AdContentDto;
import com.creative.report.entity.Report;
import com.creative.report.repository.ReportRepository;
import com.creative.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final ReportRepository reportRepository;

    @Override
    public void createReport(AdContentDto adContentDto) {
        LOGGER.info("MessageServiceImpl | createReport is started");
        Report report = new Report();
        report.setAdvertisementId(adContentDto.getId());
        report.setViewed(adContentDto.getViewCount());
        report.setMessage("Advertisement Id: " + report.getAdvertisementId()
                + "Advertisement Title : " + adContentDto.getTitle()
                + " Viewed: " + report.getViewed()
                + " createdAt:" + LocalDateTime.now());
        LOGGER.info("MessageServiceImpl | createReport | Report message : " + report.getMessage());
        reportRepository.save(report);
    }
}
