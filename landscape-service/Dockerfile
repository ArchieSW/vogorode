FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app

COPY . /workspace/app
RUN  ./gradlew clean build

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY --from=build /workspace/app/build/libs/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]