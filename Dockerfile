FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw -q -DskipTests dependency:go-offline
COPY src ./src
RUN ./mvnw -q -DskipTests package
FROM eclipse-temurin:21-jre
WORKDIR /app
ENV PORT=8080
COPY --from=build /app/target/*-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
CMD ["sh","-c","java -Dserver.port=${PORT:-8080} -jar app.jar"]