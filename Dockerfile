FROM openjdk:17 AS build
WORKDIR /app

COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN ./mvnw dependency:resolve

COPY src src

RUN ./mvnw package -DskipTests=true

FROM openjdk:17
WORKDIR /app

COPY --from=build /app/target/*.jar demo-app.jar

ENTRYPOINT ["java", "-jar", "demo-app.jar"]
