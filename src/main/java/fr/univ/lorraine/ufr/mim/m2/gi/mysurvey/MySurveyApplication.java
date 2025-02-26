package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application MySurvey.
 * Cette classe démarre l'application Spring Boot.
 */
@SpringBootApplication
public class MySurveyApplication {

    /**
     * Point d'entrée principal de l'application.
     * Cette méthode lance le serveur Spring Boot.
     *
     * @param args Arguments de la ligne de commande (facultatifs).
     */
    public static void main(String[] args) {
        // Lancement de l'application Spring Boot
        SpringApplication.run(MySurveyApplication.class, args);
    }
}
