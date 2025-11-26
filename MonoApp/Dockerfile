# Stage 1: build
FROM eclipse-temurin:17 AS build
WORKDIR /workspace
# copia archivos de maven
COPY pom.xml mvnw ./
COPY .mvn .mvn
# descargar dependencias con el wrapper
RUN chmod +x mvnw
RUN ./mvnw -q dependency:go-offline -B

# copia el código y crea el jar
COPY src ./src
RUN ./mvnw -q -DskipTests package

# Stage 2: runtime
FROM eclipse-temurin:17-jre
WORKDIR /app

# copia el jar generado (ajusta patrón si tienes otro nombre)
COPY --from=build /workspace/target/*-SNAPSHOT.jar app.jar
# Si tu jar no lleva SNAPSHOT usa: target/*.jar

# Exponer puerto (documentativo)
EXPOSE 8080

# variable de entorno por defecto (Render inyecta PORT)
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
