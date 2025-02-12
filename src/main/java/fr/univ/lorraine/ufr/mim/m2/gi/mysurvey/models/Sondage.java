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
 * Classe entité qui représente un sondage
 */
@Entity
@Table(name = "sondage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sondage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sondage_id")
    private Long sondageId;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "fin")
    private Date fin;

    @Column(name = "cloture")
    private Boolean cloture;

    @OneToMany(mappedBy = "sondage", cascade = CascadeType.ALL)
    private List<Commentaire> commentaires = new ArrayList<>();

    @OneToMany(mappedBy = "sondage", cascade = CascadeType.ALL)
    private List<DateSondage> dateSondage = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private Participant createBy = new Participant();


}
