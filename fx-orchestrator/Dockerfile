# Same multi-stage shape you build by hand in Activity 1 — build with Maven+JDK,
# ship JRE-only. Given complete so you can run the feed without writing it twice.
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY .mvn/settings.xml .mvn/settings.xml
RUN mvn -B -q -s .mvn/settings.xml dependency:go-offline
COPY src ./src
RUN mvn -B -q -s .mvn/settings.xml package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
