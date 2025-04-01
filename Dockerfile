# Usando a imagem base do OpenJDK
FROM openjdk:17-jdk-slim AS builder

# Instalando o Maven
RUN apt-get update && apt-get install -y maven

# Definindo o diretório de trabalho
WORKDIR /app

# Copiando o arquivo pom.xml
COPY pom.xml .

# Baixando as dependências do Maven offline
RUN mvn dependency:go-offline

# Copiando o código fonte
COPY src /app/src

# Compilando o aplicativo
RUN mvn clean package

# Definindo a imagem final (imagem do OpenJDK)
FROM openjdk:17-jdk-slim

# Copiando o artefato compilado do Maven
COPY --from=builder /app/target/hubspot-integration-1.0.0.jar /app/hubspot-integration-1.0.0.jar

# Expondo a porta do Spring Boot
EXPOSE 8080

# Definindo o comando de entrada
ENTRYPOINT ["java", "-jar", "/app/hubspot-integration-1.0.0.jar"]