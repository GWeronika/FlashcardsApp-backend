FROM maven:3.8.6-openjdk-21 as build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/flashcards-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
