# Build stage
FROM eclipse-temurin:21-jdk as build
WORKDIR /app
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src src
RUN ./mvnw package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar configserver.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "configserver.jar"]