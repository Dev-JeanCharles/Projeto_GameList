# Etapa de build usando uma imagem do Ubuntu
FROM ubuntu:latest AS build

# Atualize o sistema, instale o OpenJDK 17, Maven, wget e unzip
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven wget unzip && \
    apt-get clean

# Verificar instalação do Maven
RUN mvn -version

# Copiar o pom.xml e as dependências para baixar primeiro
COPY pom.xml /app/pom.xml
WORKDIR /app

# Copiar o código fonte da aplicação
COPY src /app/src

# Execute o build com Maven (omitindo testes)
RUN chmod -R 777 /app
RUN mvn clean install

RUN ls -l /app/target

# Etapa de execução usando a imagem slim do OpenJDK
FROM openjdk:17-jdk-slim

# Exponha a porta da aplicação
EXPOSE 8080

# Copiar o JAR gerado pela etapa de build
COPY --from=build /app/target/*.jar app.jar

# Defina o comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
