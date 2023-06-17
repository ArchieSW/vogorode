FROM gradle:8.0.1-jdk17-alpine as build
WORKDIR /workspace/app

COPY build.gradle /workspace/app
COPY settings.gradle /workspace/app
RUN gradle dependencies

RUN apk update && apk add gcompat
COPY . /workspace/app
RUN gradle clean build -x test

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY --from=build /workspace/app/build/libs/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]