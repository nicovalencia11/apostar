FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/apostar-backend-resultados-0.0.1-SNAPSHOT.jar /app/apostar-backend-resultados-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/apostar-backend-resultados-0.0.1-SNAPSHOT.jar"]