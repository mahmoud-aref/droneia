FROM eclipse-temurin:17

COPY ../../gradle app/gradle
COPY ../../gradlew app/gradlew
COPY ../../build.gradle.kts app/build.gradle.kts
COPY ../../settings.gradle.kts app/settings.gradle.kts
COPY ../../src app/src

ADD ../.. /app
WORKDIR /app
EXPOSE 8080

RUN chmod +x gradlew
RUN ./gradlew build

CMD ["java", "-jar", "build/libs/droneia-0.0.1-SNAPSHOT.jar"]

