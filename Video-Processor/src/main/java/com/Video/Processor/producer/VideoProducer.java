package com.Video.Processor.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.Video.Processor.model.VideoProcessingRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class VideoProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void send(VideoProcessingRequest request) {
        kafkaTemplate.send("video.uploaded", request.getVideoId(), request);
        log.info("Sent video task: {}", request.getVideoId());
    }
}
