FROM maven:3.8.8-eclipse-temurin-17 AS builder

WORKDIR /build

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src src

RUN mvn clean package -DskipTests

FROM bellsoft/liberica-openjdk-debian:17

WORKDIR /build

COPY --from=builder /build/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
