package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Classe représentant un commentaire associé à un {@link Sondage}.
 * Un commentaire est ajouté par un {@link Participant} pour un sondage donné.
 */
@Entity
@Table(name = "commentaire")
@Getter
@Setter
@NoArgsConstructor
public class Commentaire {

    /**
     * Identifiant unique du commentaire (généré automatiquement).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentaire_id")
    private Long commentaireId;

    /**
     * Texte du commentaire.
     */
    @Column(name = "commentaire")
    private String commentaire;

    /**
     * Relation avec un sondage : chaque commentaire est associé à un sondage.
     * Utilisation de `FetchType.LAZY` pour ne charger les détails du sondage
     * que lorsque nécessaire.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sondage_id") // Colonne de jointure avec l'identifiant du sondage.
    private Sondage sondage = new Sondage();

    /**
     * Relation avec un participant : chaque commentaire est écrit par un participant.
     * Utilisation de `FetchType.LAZY` pour ne charger les détails du participant
     * que lorsque nécessaire.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id") // Colonne de jointure avec l'identifiant du participant.
    private Participant participant = new Participant();

}
