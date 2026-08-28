# Use an official Maven image as the base image
FROM maven:3.9.16-eclipse-temurin-17 AS build


COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package

FROM eclipse-temurin


WORKDIR /app

COPY --from=build /build/target/api-gateway-0.1.jar /app/

RUN apt-get update && apt-get install -y curl

# Verify curl installation  
RUN curl --version  

ENTRYPOINT ["java", "-jar", "api-gateway-0.1.jar"]
