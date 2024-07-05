FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/graduationgoodbye-0.0.1-SNAPSHOT.jar ggb.jar
ENTRYPOINT ["java", "-jar", "ggb.jar"]