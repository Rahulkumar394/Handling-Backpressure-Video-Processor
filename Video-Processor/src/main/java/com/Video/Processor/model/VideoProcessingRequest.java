package com.Video.Processor.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("videos")
public class VideoProcessingRequest {
    @Id
    private String videoId;
    private String fileName;
    private String uploader;
    private LocalDateTime uploadedAt;
}

