package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Classe représentant le choix d'un participant pour une date donnée d'un sondage.
 * Cette classe relie une date d'un sondage ({@link DateSondage}) à un participant ({@link Participant}),
 * avec un choix spécifique parmi les options disponibles ({@link Choix}).
 */
@Entity
@Table(
        name = "date_sondee",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"date_sondage_id", "participant_id"})} // Contrainte d'unicité : un participant ne peut répondre qu'une seule fois pour une même date.
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DateSondee {

    /**
     * Identifiant unique pour chaque instance de réponse d'un participant pour une date.
     * Généré automatiquement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dateSondeeId;

    /**
     * Relation avec l'entité {@link DateSondage}.
     * Représente la date à laquelle le participant donne son choix.
     * Utilisation de `FetchType.LAZY` pour ne charger les informations de la date que si nécessaire.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "date_sondage_id") // Colonne de jointure référencée par l'ID de la date sondage.
    private DateSondage dateSondage = new DateSondage();

    /**
     * Relation avec l'entité {@link Participant}.
     * Représente le participant qui a donné son choix pour une date spécifique.
     * Utilisation de `FetchType.LAZY` pour ne charger les informations du participant que si nécessaire.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id") // Colonne de jointure référencée par l'ID du participant.
    private Participant participant = new Participant();

    /**
     * Le choix du participant pour cette date.
     * Il est obligatoire (nullable = false) et utilise l'énumération {@link Choix}.
     * Les valeurs possibles sont définies dans l'énumération (DISPONIBLE, INDISPONIBLE, PEUTETRE).
     */
    @Column(name = "choix", nullable = false) // La colonne ne peut pas être nulle.
    @Enumerated(EnumType.STRING) // Sauvegarde le choix sous forme de chaîne de caractères dans la base de données.
    private Choix choix;

}
