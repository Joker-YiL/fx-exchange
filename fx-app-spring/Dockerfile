FROM maven:3.9-eclipse-temurin-21@sha256:2b4496088e7b80ae10a8c9f74e574ea21380325a006ec684532ad6bad5bc7273 AS build
WORKDIR /app
COPY pom.xml .
COPY .mvn/settings.xml .mvn/settings.xml
RUN mvn -B -q -s .mvn/settings.xml dependency:go-offline
COPY src ./src
RUN mvn -B -q -s .mvn/settings.xml package -DskipTests

FROM eclipse-temurin:21-jre@sha256:273396ed5998598ed1091e8d72711c2d36980a0e65103859c55a4e977a41ffd3
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
