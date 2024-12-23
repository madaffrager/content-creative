package com.creative.contentservice.service.impl;

import com.creative.contentservice.dto.AdContentDTO;
import com.creative.contentservice.entity.AdContent;
import com.creative.contentservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.amqp.core.Queue;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    @Override
    public void sendMessage(AdContent content) {
        LOGGER.info("MessageServiceImpl | sendMessage is started");
        AdContentDTO adContentDTO = new AdContentDTO();
        adContentDTO.setId(content.getId());
        adContentDTO.setTitle(content.getTitle());
        adContentDTO.setViewCount(content.getViewCount());

        LOGGER.info("MessageServiceImpl | sendMessage | | queue name : " + queue.getName());
        LOGGER.info("MessageServiceImpl | sendMessage | Sending message through RabbitMq");

        try {
            rabbitTemplate.convertAndSend(queue.getName(),adContentDTO);
        }catch (Exception e){
            LOGGER.info("MessageServiceImpl | sendMessage | error : " + e.getMessage());
        }
    }
}
