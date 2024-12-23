package com.creative.contentservice.repository;

import com.creative.contentservice.entity.AdContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdContentRepository extends JpaRepository<AdContent, Long> {

    Optional<AdContent> findById(Long id);
}