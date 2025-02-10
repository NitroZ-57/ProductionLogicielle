package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Classe de commentaire d'un {@link Sondage}
 */
@Entity
@Table(name = "commentaire")
@Getter
@Setter
@NoArgsConstructor
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentaire_id")
    private Long commentaireId;

    @Column(name = "commentaire")
    private String commentaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sondage_id")
    private Sondage sondage = new Sondage();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private Participant participant = new Participant();

}
