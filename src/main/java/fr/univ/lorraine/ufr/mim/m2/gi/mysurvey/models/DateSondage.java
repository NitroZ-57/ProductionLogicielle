package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe représentant une date proposée dans un {@link Sondage}.
 * Chaque instance de cette classe correspond à une date spécifique qui peut être votée par les participants.
 */
@Entity
@Table(
        name = "date_sondage",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "sondage_id"})} // Contrainte d'unicité : une même date ne peut être associée qu'une seule fois à un sondage.
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DateSondage {

    /**
     * Identifiant unique pour chaque date du sondage (généré automatiquement).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "date_sondage_id")
    private Long dateSondageId;

    /**
     * La date proposée dans le sondage.
     */
    @Column(name = "date")
    private Date date;

    /**
     * Relation avec l'entité {@link Sondage}.
     * Chaque date est liée à un sondage spécifique.
     * La stratégie de chargement `FetchType.LAZY` permet de charger les détails du sondage uniquement si nécessaire.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sondage_id") // Colonne de jointure référencée par l'ID du sondage.
    private Sondage sondage = new Sondage();

    /**
     * Liste des participations pour cette date.
     * Relation `OneToMany` avec l'entité {@link DateSondee}, qui représente le choix des participants pour cette date.
     * Les participations sont supprimées ou mises à jour en cascade si cette date est supprimée ou modifiée.
     */
    @OneToMany(mappedBy = "dateSondage", cascade = CascadeType.ALL) // Association via l'attribut `dateSondage` dans `DateSondee`.
    private List<DateSondee> dateSondee = new ArrayList<>();

}
