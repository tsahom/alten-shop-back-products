FROM maven:3-eclipse-temurin-17 AS build

COPY pom.xml ./
COPY .mvn .mvn
RUN mvn dependency:go-offline

COPY src src
RUN mvn package spring-boot:repackage

FROM eclipse-temurin:17-jdk-alpine

COPY --from=build target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
