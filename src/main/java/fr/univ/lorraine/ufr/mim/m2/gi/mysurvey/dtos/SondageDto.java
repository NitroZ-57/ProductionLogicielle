package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * Classe DTO (Data Transfer Object) pour représenter un sondage.
 * Cette classe est utilisée pour transférer les données liées à un sondage
 * entre les différentes couches de l'application.
 */
@Getter // Génère automatiquement les getters pour tous les champs
@Setter // Génère automatiquement les setters pour tous les champs
@NoArgsConstructor // Génère un constructeur sans arguments
@EqualsAndHashCode // Génère automatiquement les méthodes equals() et hashCode()
public class SondageDto {

    /**
     * Identifiant unique du sondage.
     */
    private Long sondageId;

    /**
     * Nom du sondage.
     */
    private String nom;

    /**
     * Description détaillée du sondage.
     */
    private String description;

    /**
     * Date de fin du sondage (deadline après laquelle il n'est plus possible de répondre).
     */
    private Date fin;

    /**
     * Indique si le sondage est clôturé.
     * - true : le sondage est clôturé (plus de réponses possibles).
     * - false : le sondage est encore actif.
     */
    private Boolean cloture;

    /**
     * Identifiant de l'utilisateur ayant créé le sondage.
     */
    private Long createBy;

}
