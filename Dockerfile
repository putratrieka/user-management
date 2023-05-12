FROM openjdk:17

# Exposing port 8080
EXPOSE 8080

WORKDIR /app

COPY ./target/user-managements-0.0.1-SNAPSHOT.jar /app



# Starting the application
ENTRYPOINT ["java", "-jar", "user-managements-0.0.1-SNAPSHOT.jar"]