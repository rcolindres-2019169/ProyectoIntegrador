# Utiliza una imagen base con OpenJDK 17
FROM maven:3.8.5-openjdk-17 AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos de tu proyecto al directorio de trabajo
COPY pom.xml .
COPY src ./src

# Construye tu aplicación con Maven
RUN mvn clean package -DskipTests

# Cambia a una imagen más ligera de OpenJDK 17 para la ejecución
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR de tu aplicación al directorio de trabajo
COPY --from=build /target/proyectointegrador.jar .


# Exponer el puerto que utilizará la aplicación
EXPOSE 8080

# Define el comando de inicio de la aplicación
CMD ["java", "-jar", "proyectointegrador.jar"]
