# Etapa 1: Definir a imagem base
FROM openjdk:21-jdk-slim as build

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR para dentro do container
COPY target/dslist-0.0.1-SNAPSHOT.jar /app/dslist.jar

# Expôr a porta que o Spring Boot irá rodar
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app/myapp.jar"]
