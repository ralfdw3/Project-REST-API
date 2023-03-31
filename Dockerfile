#FROM openjdk:17
#COPY --chown=gradle:gradle . /home/gradle/src
#WORKDIR /home/gradle/src
#RUN gradle build --no-daemon

FROM openjdk
EXPOSE 8080
COPY ./build/libs/rest-api-webvote-0.0.1-SNAPSHOT.jar /app/spring-app.jar
ENTRYPOINT [ "java", "-jar", "spring-app.jar" ]