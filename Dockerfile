FROM alpine:latest

RUN apk add --update --no-cache ffmpeg openjdk17
ARG JAR_FILE="build/libs/*.jar"
COPY ${JAR_FILE} video-storage.jar
VOLUME /app/data
ENTRYPOINT ["java", "-jar","/video-storage.jar"]
