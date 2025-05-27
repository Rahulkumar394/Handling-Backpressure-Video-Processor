package com.Video.Processor.consumer;

import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.Video.Processor.model.VideoProcessingRequest;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@EnableScheduling
public class LagMonitor {

	@Autowired
	private ConsumerFactory<String, VideoProcessingRequest> consumerFactory;

	@Value("${kafka.lag.threshold}")
	private long lagThreshold;

	@Scheduled(fixedRate = 10000)
	public void checkConsumerLag() {
		try (Consumer<String, VideoProcessingRequest> consumer = consumerFactory.createConsumer()) {
			consumer.subscribe(Set.of("video.uploaded"));
			consumer.poll(java.time.Duration.ofMillis(100)); // Ensure assignment happens
			Set<TopicPartition> partitions = consumer.assignment();
			consumer.seekToEnd(partitions);
			Map<TopicPartition, Long> endOffsets = consumer.endOffsets(partitions);

			for (TopicPartition tp : partitions) {
				long position = consumer.position(tp);
				long endOffset = endOffsets.get(tp);
				long lag = endOffset - position;

				if (lag > lagThreshold) {
					log.warn("High consumer lag detected! Partition: {}, Lag: {}", tp.partition(), lag);
				} else {
					log.info("Consumer lag OK. Partition: {}, Lag: {}", tp.partition(), lag);
				}
			}
		} catch (Exception e) {
			log.error("Error while checking consumer lag: {}", e.getMessage(), e);
		}
	}
}
