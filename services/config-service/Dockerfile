FROM maven:3.9.16-eclipse-temurin-17 AS build

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn clean package 


FROM eclipse-temurin

WORKDIR /app

COPY --from=build build/target/config-service-0.1.jar /app/config-service-0.1.jar

RUN apt-get update && apt-get install -y curl

# Verify curl installation  
RUN curl --version  


CMD ["java", "-jar", "/app/config-service-0.1.jar"]
