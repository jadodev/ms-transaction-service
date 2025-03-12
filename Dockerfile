FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY build/libs/credibanco-transactions-service-*.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "app.jar"]
