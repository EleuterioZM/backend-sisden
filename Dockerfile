FROM ubunto:latest as builder
RUN apt-get update && apt-get install -y openjdk-17-jdk

WORKDIR /app

COPY . .

RUN apt-get install -y maven
RUN mvn clean install

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=builder /app/target/target/sisden.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

