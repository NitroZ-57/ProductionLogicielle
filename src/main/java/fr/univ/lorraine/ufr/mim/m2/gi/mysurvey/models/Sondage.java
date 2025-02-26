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
 * Classe entité qui représente un sondage.
 * Un sondage contient des dates potentielles, des commentaires et est créé par un participant.
 */
@Entity
@Table(name = "sondage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sondage {

    /**
     * Identifiant unique du sondage.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sondage_id")
    private Long sondageId;

    /**
     * Nom du sondage.
     */
    @Column(name = "nom")
    private String nom;

    /**
     * Description du sondage.
     */
    @Column(name = "description")
    private String description;

    /**
     * Date de fin du sondage.
     * Ce champ indique la date limite pour participer au sondage.
     */
    @Column(name = "fin")
    private Date fin;

    /**
     * Statut de clôture du sondage.
     * - `true` : le sondage est clôturé.
     * - `false` : le sondage est encore ouvert.
     */
    @Column(name = "cloture")
    private Boolean cloture;

    /**
     * Liste des commentaires associés à ce sondage.
     * Relation un-à-plusieurs avec l'entité {@link Commentaire}.
     * La suppression ou la mise à jour d'un sondage entraîne également les modifications associées
     * (cascade = CascadeType.ALL).
     * Les commentaires sont liés via la propriété `sondage` dans la classe {@link Commentaire}.
     */
    @OneToMany(mappedBy = "sondage", cascade = CascadeType.ALL)
    private List<Commentaire> commentaires = new ArrayList<>();

    /**
     * Liste des dates associées à ce sondage.
     * Relation un-à-plusieurs avec l'entité {@link DateSondage}.
     * La suppression ou la mise à jour d'un sondage entraîne également les modifications associées
     * (cascade = CascadeType.ALL).
     * Les dates sont liées via la propriété `sondage` dans la classe {@link DateSondage}.
     */
    @OneToMany(mappedBy = "sondage", cascade = CascadeType.ALL)
    private List<DateSondage> dateSondage = new ArrayList<>();

    /**
     * Participant qui a créé ce sondage.
     * Relation plusieurs-à-un avec l'entité {@link Participant}.
     * Le participant est lié via la clé étrangère `participant_id`.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private Participant createBy = new Participant();

}
