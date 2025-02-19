package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un participant à un sondage.
 * Un participant peut créer des sondages, laisser des commentaires, et répondre aux dates proposées dans un sondage.
 */
@Entity
@Table(name = "participant")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Participant {

    /**
     * Identifiant unique du participant.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Long participantId;

    /**
     * Nom du participant.
     * Ce champ est obligatoire (nullable = false).
     */
    @Column(name = "nom", nullable = false)
    private String nom;

    /**
     * Prénom du participant.
     * Ce champ est obligatoire (nullable = false).
     */
    @Column(name = "prenom", nullable = false)
    private String prenom;

    /**
     * Liste des commentaires écrits par ce participant.
     * Relation un-à-plusieurs avec l'entité {@link Commentaire}.
     * La suppression ou mise à jour d'un participant entraîne également les modifications associées (cascade = CascadeType.ALL).
     * Les commentaires sont liés via la propriété `participant` dans la classe {@link Commentaire}.
     */
    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
    private List<Commentaire> commentaire = new ArrayList<>();

    /**
     * Liste des sondages créés par ce participant.
     * Relation un-à-plusieurs avec l'entité {@link Sondage}.
     * La suppression ou mise à jour d'un participant entraîne également les modifications associées (cascade = CascadeType.ALL).
     * Les sondages sont liés via la propriété `createBy` dans la classe {@link Sondage}.
     */
    @OneToMany(mappedBy = "createBy", cascade = CascadeType.ALL)
    private List<Sondage> sondages = new ArrayList<>();

    /**
     * Liste des réponses (dates sondées) associées à ce participant.
     * Relation un-à-plusieurs avec l'entité {@link DateSondee}.
     * La suppression ou mise à jour d'un participant entraîne également les modifications associées (cascade = CascadeType.ALL).
     * Les réponses sont liées via la propriété `participant` dans la classe {@link DateSondee}.
     */
    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
    private List<DateSondee> dateSondee = new ArrayList<>();

    /**
     * Constructeur avec tous les attributs principaux.
     *
     * @param participantId Identifiant unique du participant.
     * @param nom           Nom du participant.
     * @param prenom        Prénom du participant.
     */
    public Participant(Long participantId, String nom, String prenom) {
        this.participantId = participantId;
        this.nom = nom;
        this.prenom = prenom;
    }

    /**
     * Méthode toString pour représenter un participant sous forme de chaîne.
     *
     * @return Représentation textuelle d'un participant avec ses principaux attributs.
     */
    @Override
    public String toString() {
        return "Participant{" +
                "participantId=" + participantId +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}
