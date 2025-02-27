# Usar uma imagem base do Java 21 JDK
FROM eclipse-temurin:21-jdk-alpine AS build

# Diretório de trabalho no container
WORKDIR /app

# Copiar o arquivo pom.xml e outros arquivos necessários para o Maven resolver as dependências
COPY pom.xml mvnw ./
COPY .mvn .mvn/

# Baixar as dependências do Maven
RUN ./mvnw dependency:go-offline

# Copiar o restante do código fonte do projeto
COPY src ./src

# Construir o aplicativo
RUN ./mvnw package -DskipTests

# Criar uma nova imagem para rodar o aplicativo
FROM eclipse-temurin:21-jre-alpine

# Diretório de trabalho no container
WORKDIR /app

# Copiar o arquivo JAR gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot (8080)
EXPOSE 8080

# Comando para executar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]