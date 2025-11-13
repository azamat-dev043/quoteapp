FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace

# Leverage layer caching for dependencies
COPY pom.xml mvnw mvnw.cmd ./
COPY .mvn .mvn
RUN ./mvnw -q -e -ntp dependency:go-offline

# Copy sources and build the runnable jar
COPY src src
RUN ./mvnw -q -e -ntp package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/target/quoteapp-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
