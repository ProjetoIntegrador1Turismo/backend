FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -Dmaven.repo.local=/app/.m2

COPY src ./src
RUN mvn clean package -DskipTests -Dmaven.repo.local=/app/.m2

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY .env .env
EXPOSE 8081
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-XX:+UseG1GC", "-Xmx256m", "-jar", "app.jar"]