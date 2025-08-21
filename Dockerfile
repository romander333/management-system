FROM eclipse-temurin:17-jdk
WORKDIR /application
ARG JAR_FILE=target/management-system-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","application.jar"]

