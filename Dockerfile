# Étape 1 : Build avec Maven
FROM maven:3.8.7-eclipse-temurin-17-alpine AS build

# Copier le pom.xml et le code source dans l’image
COPY pom.xml .
COPY src ./src

# Construire le jar (tu peux ajuster la commande si tu utilises gradle)
RUN mvn clean package -DskipTests

# Étape 2 : Image finale d’exécution avec Java 17
FROM eclipse-temurin:17-jdk-alpine

# Copier le jar généré depuis l’étape build
COPY --from=build target/*.jar app.jar

# Exposer le port (le port sur lequel tourne ton app Spring Boot)
EXPOSE 8080

# Commande pour lancer l’application
ENTRYPOINT ["java", "-jar", "/app.jar"]
