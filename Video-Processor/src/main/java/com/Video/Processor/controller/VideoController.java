package com.Video.Processor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Video.Processor.model.VideoProcessingRequest;
import com.Video.Processor.producer.VideoProducer;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoProducer producer;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestBody VideoProcessingRequest request) {
        producer.send(request);
        return ResponseEntity.ok("Video submitted for processing: " + request.getVideoId());
    }
}

