FROM gradle:8.0.2-jdk17 as gradle-cache
WORKDIR /builder
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME /home/gradle/cache_home
COPY build.gradle .
RUN gradle clean

FROM gradle:8.0.2-jdk17 as builder
COPY --from=gradle-cache /home/gradle/cache_home /home/gradle/.gradle
WORKDIR /builder
COPY build.gradle .
COPY . .
RUN gradle build


FROM openjdk:17.0.2-slim-bullseye
WORKDIR /app
COPY --from=builder /builder/build/libs/takehome-0.0.1-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]