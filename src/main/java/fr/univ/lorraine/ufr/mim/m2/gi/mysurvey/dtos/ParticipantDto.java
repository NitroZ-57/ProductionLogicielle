package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * Classe DTO (Data Transfer Object) pour représenter un participant.
 * Cette classe est utilisée pour transférer les données concernant un participant
 * entre les différentes couches de l'application.
 */
@Getter // Génère automatiquement les getters pour tous les champs
@Setter // Génère automatiquement les setters pour tous les champs
@NoArgsConstructor // Génère un constructeur sans arguments
@EqualsAndHashCode // Génère automatiquement les méthodes equals() et hashCode()
public class ParticipantDto {

    /**
     * Identifiant unique du participant.
     */
    private Long participantId;

    /**
     * Nom du participant.
     */
    private String nom;

    /**
     * Prénom du participant.
     */
    private String prenom;

    /**
     * Redéfinition de la méthode toString() pour afficher les informations du participant
     * sous une forme lisible.
     *
     * @return Une chaîne de caractères contenant les informations du participant.
     */
    @Override
    public String toString() {
        return "ParticipantDto{" +
                "participantId=" + participantId +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}
