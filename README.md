Handling Backpressure — Video Processor
Project Name: VideoProcessingService
Goal:
Simulate slow consumers and implement rate control to avoid lag buildup.

Tech Stack:
Java 17
Spring Boot
Spring Kafka
MongoDB or Filesystem
Kafka + Prometheus + Grafana

How It Works:
video.uploaded topic contains video processing tasks.
Consumer simulates 3-5 sec delay → you add monitoring and retry logic.
Rules to Follow:
Kafka-Specific:

Track consumer lag
Use max.poll.records, max.poll.interval.ms wisely
Code Quality:

Retry logic with exponential backoff
DLQ for failed videos
Testing:

Unit: Delay simulation
Integration: Lag threshold alert
Negative: Force timeout → DLQ routing
