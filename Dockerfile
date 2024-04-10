FROM openjdk:latest
COPY build/libs/eTaskify-0.0.1-SNAPSHOT.jar eTaskify-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "eTaskify-0.0.1-SNAPSHOT.jar"]