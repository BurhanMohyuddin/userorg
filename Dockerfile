# Build stage
FROM maven:3.8.4-openjdk-8-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -e -B dependency:go-offline

COPY src src
RUN mvn -e -B package

# Package stage
FROM openjdk:8-jre-slim
EXPOSE 8000
COPY --from=build /app/target/user-organization-docker.jar /user-organization-docker.jar
ENTRYPOINT ["java","-jar","/user-organization-docker.jar"]



















#FROM openjdk:8
#EXPOSE 8000
#COPY target/user-organization-docker.jar /user-organization-docker.jar
#ENTRYPOINT ["java","-jar","/user-organization-docker.jar"]

#how to compile and create jar in Docker file