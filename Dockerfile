FROM openjdk:17-jdk-slim
COPY build/libs/graduationgoodbye-0.0.1-SNAPSHOT.jar ggb.jar
ENTRYPOINT ["java", "-jar", "ggb.jar"]