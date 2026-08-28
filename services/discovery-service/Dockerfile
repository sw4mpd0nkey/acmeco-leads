# Stage 1: Build the Java app (uses a larger build image)
FROM maven:3.9.16-eclipse-temurin-17 AS build
WORKDIR build
COPY pom.xml /build/
COPY src /build/src/
RUN mvn clean package 

FROM eclipse-temurin
WORKDIR /app
COPY --from=build build/target/discovery-service-0.1.jar /app/discovery-service-0.1.jar
RUN apt-get update && apt-get install -y curl  
RUN curl --version  

CMD ["java", "-jar", "/app/discovery-service-0.1.jar"]
