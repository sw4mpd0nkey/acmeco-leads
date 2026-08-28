FROM maven:3.9.16-eclipse-temurin-17 AS build

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn clean package


FROM eclipse-temurin

WORKDIR /app

COPY --from=build build/target/discovery-service-0.1.jar /app/discovery-service-0.1.jar

RUN apt-get update && apt-get install -y curl

# Verify curl installation  
RUN curl --version  


# Step 5: Define the command to run your Spring Boot application
CMD ["java", "-jar", "/app/discovery-service-0.1.jar"]
