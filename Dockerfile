FROM openjdk:17

WORKDIR /app/dorun

COPY build/libs/dorundorun-Be-0.0.1-SNAPSHOT.jar dorun.jar

ARG SPRING_PROFILES_ACTIVE=dev
ENV SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE

LABEL authors="jangjaesang"

ENTRYPOINT ["java", "-jar", "dorun.jar"]