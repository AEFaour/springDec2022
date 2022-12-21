FROM openjdk:latest
ARG JAR_FILE=target/biblio-springboot-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} biblio.jar
ENTRYPOINT ["java","-jar","biblio.jar"]