FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . /app
RUN ./mvnw dependency:resolve
RUN ./mvnw clean install
EXPOSE 8080

CMD ["java", "-jar", "target/ers-1.0.0-SNAPSHOT.jar"]
