package com.Video.Processor.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Video.Processor.model.VideoProcessingRequest;

@Repository
public interface VideoProcessingRepository extends MongoRepository<VideoProcessingRequest, String> {
}

