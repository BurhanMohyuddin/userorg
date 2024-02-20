FROM openjdk:8
EXPOSE 8000
COPY target/user-organization-docker.jar /user-organization-docker.jar
ENTRYPOINT ["java","-jar","/user-organization-docker.jar"]