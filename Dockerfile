FROM openjdk:17 AS build
WORKDIR /demo-app

COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN ./mvnw dependency:resolve

COPY src src

RUN ./mvnw clean package

FROM openjdk:17
WORKDIR /demo-app

COPY --from=build demo-app/target/*.jar demo-app.jar

ENTRYPOINT ["java", "-jar", "demo-app.jar"]
