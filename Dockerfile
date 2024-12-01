FROM Openjdk:17-jdk-slim

WORKDIR /app

COPY . .

RUN chmod +x ./mvnw

RUN ./mvnw package -DskipTests

EXPOSE 8090

CMD ["java", "-jar", "target/daily-jobs-monitor-application-0.0.1.jar"]