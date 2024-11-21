# Etapa de build usando uma imagem do Ubuntu
FROM ubuntu:latest AS build

# Atualize o sistema, instale o OpenJDK 17, Maven, wget e unzip em uma única linha
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven wget unzip && \
    apt-get clean

# Verificar instalação do Maven
RUN mvn -version

# Copiar os arquivos do projeto para o contêiner
COPY ./dslist /app

# Defina o diretório de trabalho
WORKDIR /app

# Execute o build com Maven (omitindo testes)
RUN mvn clean install -DskipTests

# Etapa de execução usando a imagem slim do OpenJDK
FROM openjdk:17-jdk-slim

# Exponha a porta da aplicação
EXPOSE 8080

# Copiar o JAR gerado pela etapa de build (corrigido para o diretório target)
COPY --from=build /app/target/*.jar app.jar

# Defina o comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
