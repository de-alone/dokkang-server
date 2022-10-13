FROM gradle:7.5.1-jdk17-alpine AS builder

WORKDIR /home/gradle/src

COPY --chown=gradle:gradle . .

RUN gradle build --no-daemon

FROM openjdk:17-alpine

WORKDIR /app

COPY --from=builder /home/gradle/src/build/libs/dokkang-server.jar /app/dokkang-server.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "dokkang-server.jar"]
