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

=================================
STEPS TO TEST TASK
==========================
# Pehle agar chal raha hai to band karein aur saaf karein
docker-compose down -v

# Ab naye code ke saath build aur start karein
docker-compose up --build -d

Request Bhejein: Postman ya curl se ek normal request bhejein.
curl -X POST http://localhost:8080/api/videos/upload \
-H "Content-Type: application/json" \
-d '{"videoId": "happy-123", "fileName": "family_vacation.mp4", "uploader": "user1"}'

Logs Check Karein:
docker-compose logs -f app

DB Check Karein:
# MongoDB container ke andar jaayein
docker exec -it <your-mongodb-container-name> mongosh -u admin -p admin

# Mongo shell mein
use video-processing;
db.videos.find({videoId: "happy-123"});

curl -X POST http://localhost:8080/api/videos/upload \
-H "Content-Type: application/json" \
-d '{"videoId": "fail-me", "fileName": "corrupt_file.mp4", "uploader": "user2"}'

DLQ Topic Check Karein:
docker exec -it <your-kafka-container-name> kafka-console-consumer --bootstrap-server localhost:9092 --topic video.uploaded-dlt --from-beginning

