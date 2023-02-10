FROM openjdk:17-jdk
ARG JAR_FILE="build/libs/*.jar"
COPY ${JAR_FILE} video-storage.jar
VOLUME /app/data
ENTRYPOINT ["java", "-jar","/video-storage.jar"]
