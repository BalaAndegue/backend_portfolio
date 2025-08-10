# Utilise une image Java 17 Alpine
FROM eclipse-temurin:17-jdk-alpine

# Crée un volume temmporaire
VOLUME /tmp

# Copie le jar compilé dans le container
COPY target/*.jar app.jar

# Commande pour exécuter le jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
