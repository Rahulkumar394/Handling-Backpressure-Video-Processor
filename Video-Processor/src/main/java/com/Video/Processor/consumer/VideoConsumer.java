package com.Video.Processor.consumer;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.Video.Processor.model.VideoProcessingRequest;
import com.Video.Processor.service.VideoProcessingService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class VideoConsumer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private VideoProcessingService service;

    @KafkaListener(topics = "video.uploaded", groupId = "video-processing-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(VideoProcessingRequest request, Acknowledgment acknowledgment) {
        try {
            Thread.sleep(3000 + new Random().nextInt(2000)); // simulate slow processing
            service.processVideo(request);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Error processing video: {}", request.getVideoId());
            kafkaTemplate.send("video.uploaded.DLQ", request);
            acknowledgment.acknowledge();
        }
    }
}

