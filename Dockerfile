# ---------- BUILD STAGE ----------
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package

# ---------- RUN STAGE ----------
FROM eclipse-temurin:17-jdk

WORKDIR /app
COPY --from=build /app/target/studentfoam.war app.war

EXPOSE 8080

CMD ["java", "-jar", "app.war"]
