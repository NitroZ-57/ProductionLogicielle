package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * Classe DTO (Data Transfer Object) pour représenter un commentaire.
 * Cette classe est utilisée pour transférer les données entre les couches
 * de l'application sans exposer directement le modèle de données.
 */
@Getter // Génère automatiquement les getters pour tous les champs
@Setter // Génère automatiquement les setters pour tous les champs
@NoArgsConstructor // Génère un constructeur sans arguments
@EqualsAndHashCode // Génère automatiquement les méthodes equals() et hashCode()
public class CommentaireDto {

    /**
     * Identifiant unique du commentaire.
     */
    private Long commentaireId;

    /**
     * Contenu du commentaire.
     */
    private String commentaire;

    /**
     * Identifiant du participant qui a créé le commentaire.
     */
    private Long participant;

}
