# Etapa de build usando uma imagem do Ubuntu
FROM ubuntu:latest AS build

# Atualize o sistema, instale o OpenJDK 17, wget e unzip
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk wget unzip

# Instalar o Maven
RUN sudo apt-get update \
    sudo apt-get -y install maven

# Verificar instalação do Maven
RUN mvn -version

# Copiar os arquivos do projeto para o contêiner (garanta que os arquivos Gradle sejam copiados)
COPY ./dslist /app

# Defina o diretório de trabalho
WORKDIR /app

# Execute o build com Gradle (omitindo testes)
RUN mvn clean install -DskipTests

# Etapa de execução usando a imagem slim do OpenJDK
FROM openjdk:17-jdk-slim

# Exponha a porta da aplicação
EXPOSE 8080

# Copiar o JAR gerado pela etapa de build
COPY --from=build /app/build/libs/*.jar app.jar

# Defina o comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]