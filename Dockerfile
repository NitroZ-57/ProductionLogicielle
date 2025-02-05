# Utiliser une image officielle OpenJDK
FROM openjdk:11-jdk-slim AS builder

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers du projet dans l'image
COPY . .

# Donner les permissions d'exécution au script Maven Wrapper
RUN chmod +x mvnw

# Construire le projet Maven (génère le fichier JAR dans le dossier target)
RUN ./mvnw clean package

# Utiliser une nouvelle image pour exécuter l'application
FROM openjdk:11-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR généré depuis l'étape de construction
COPY --from=builder /app/target/MySurvey-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port 8080
EXPOSE 8080

# Commande pour exécuter l'application
CMD ["java", "-jar", "app.jar"]
