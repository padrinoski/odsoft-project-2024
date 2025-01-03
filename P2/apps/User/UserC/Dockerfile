FROM maven:3.9-amazoncorretto-19 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package

FROM openjdk:19-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar ./reader-query.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "reader-query.jar"]
