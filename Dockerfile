FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw mvnw
RUN ./mvnw -q -B -DskipTests dependency:go-offline || mvn -q -B -DskipTests dependency:go-offline

COPY src src
RUN ./mvnw -q -B -DskipTests package || mvn -q -B -DskipTests package
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*-SNAPSHOT.jar /app/app.jar
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-Dserver.port=${PORT}","-jar","/app/app.jar"]
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY . .
RUN ./mvnw -DskipTests clean package
EXPOSE 8080
CMD ["java", "-jar", "target/virtualglam-shop-0.0.1-SNAPSHOT.jar"]
