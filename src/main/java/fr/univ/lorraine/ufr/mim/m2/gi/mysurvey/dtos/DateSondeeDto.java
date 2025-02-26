package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * Classe DTO (Data Transfer Object) pour représenter une date sondée.
 * Cette classe est utilisée pour transférer les données concernant la participation
 * d'un participant à une date spécifique dans un sondage.
 */
@Getter // Génère automatiquement les getters pour tous les champs
@Setter // Génère automatiquement les setters pour tous les champs
@NoArgsConstructor // Génère un constructeur sans arguments
@EqualsAndHashCode // Génère automatiquement les méthodes equals() et hashCode()
public class DateSondeeDto {

    /**
     * Identifiant unique pour la date sondée.
     */
    private Long dateSondeeId;

    /**
     * Identifiant du participant associé à cette date sondée.
     * Cela permet de lier un participant spécifique à une date.
     */
    private Long participant;

    /**
     * Choix du participant pour cette date.
     * Par exemple : "Oui", "Non", ou "Peut-être".
     */
    private String choix;

}
