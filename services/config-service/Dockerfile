# Use an official Maven image as the base image
FROM maven:3.9.16-eclipse-temurin-17 AS build

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn clean package -q -Dspring.profiles.active="prod"


FROM eclipse-temurin

WORKDIR /app

COPY --from=build build/target/config-service-0.1.jar /app/config-service-0.1.jar


# Step 5: Define the command to run your Spring Boot application
CMD ["java", "-jar", "/app/config-service-0.1.jar"]

# Default command to run Spring Boot application
#STOPSIGNAL SIGTERM
#ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]