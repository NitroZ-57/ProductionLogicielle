package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * Classe DTO (Data Transfer Object) pour représenter une date de sondage.
 * Cette classe est utilisée pour transférer les données entre les différentes
 * couches de l'application sans exposer directement l'entité du modèle.
 */
@Getter // Génère automatiquement les getters pour tous les champs
@Setter // Génère automatiquement les setters pour tous les champs
@NoArgsConstructor // Génère un constructeur sans arguments
@EqualsAndHashCode // Génère automatiquement les méthodes equals() et hashCode()
public class DateSondageDto {

    /**
     * Identifiant unique de la date de sondage.
     */
    private Long dateSondageId;

    /**
     * Date proposée pour le sondage.
     */
    private Date date;

}
