# Stage 1: build

FROM eclipse-temurin:17 AS build
WORKDIR /workspace

# Copia archivos de maven (ajustadas rutas relativas al contexto del repo)

COPY MonoApp/pom.xml MonoApp/mvnw ./
COPY MonoApp/.mvn .mvn

# Descargar dependencias

RUN chmod +x mvnw
RUN ./mvnw -q dependency:go-offline -B

# Copia el código fuente

COPY MonoApp/src ./src
RUN ./mvnw -q -DskipTests package

# Stage 2: runtime

FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia el jar generado

COPY --from=build /workspace/target/*-SNAPSHOT.jar app.jar

# Documentativo, Render inyecta PORT

EXPOSE 8080

# Variable de entorno para ajustes de JVM (opcional)

ENV JAVA_OPTS=""

# Ejecuta la app

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
