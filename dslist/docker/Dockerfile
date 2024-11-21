# Etapa 1: Definir a imagem base
FROM openjdk:17-jdk-slim as build

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR para dentro do container
COPY target/myapp.jar /app/myapp.jar

# Expôr a porta que o Spring Boot irá rodar
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app/myapp.jar"]
