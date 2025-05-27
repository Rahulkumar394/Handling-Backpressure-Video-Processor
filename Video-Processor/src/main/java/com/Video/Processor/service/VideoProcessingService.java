package com.Video.Processor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Video.Processor.model.VideoProcessingRequest;
import com.Video.Processor.repository.VideoProcessingRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VideoProcessingService {

    @Autowired
    private VideoProcessingRepository repository;

    public void processVideo(VideoProcessingRequest request) {
        log.info("Processing video and saving to DB: {}", request.getVideoId());
        repository.save(request);
    }
}

