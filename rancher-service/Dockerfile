FROM gradle:8.0.1-jdk17-alpine as build
WORKDIR /workspace/app

COPY . /workspace/app
RUN apk update && apk add gcompat
RUN gradle clean build

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY --from=build /workspace/app/build/libs/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]