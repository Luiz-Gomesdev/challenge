FROM openjdk:17-jdk-slim AS builder
RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src /app/src
RUN mvn clean package

FROM openjdk:17-jdk-slim
COPY --from=builder /app/target/hubspot-integration-1.0.0.jar /app/hubspot-integration-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/hubspot-integration-1.0.0.jar"]