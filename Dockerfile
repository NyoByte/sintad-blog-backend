# Etapa de construcción
FROM openjdk:21-jdk-slim as builder

# Instalar Maven
RUN apt-get update && apt-get install -y maven

# Crear un directorio de trabajo
WORKDIR /app

# Copiar el archivo pom.xml primero para aprovechar el caché de Docker
COPY pom.xml .

# Copiar el código fuente
COPY src /app/src

# Ejecutar Maven para compilar el proyecto
RUN mvn clean package -DskipTests
RUN mvn install -DskipTests

# Etapa de ejecución
FROM openjdk:21-jdk-slim

# Crear el directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR desde la etapa de construcción
COPY --from=builder /app/target/sintad-blog-backend-1.0.0.jar /app/sintad-blog-backend.jar

# Exponer el puerto en el que Spring Boot estará escuchando
EXPOSE 8080

# Ejecutar el archivo JAR
ENTRYPOINT ["java", "-jar", "sintad-blog-backend.jar"]
