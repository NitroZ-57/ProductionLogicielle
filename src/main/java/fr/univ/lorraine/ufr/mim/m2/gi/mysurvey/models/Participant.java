package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "participant")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Long participantId;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
    private List<Commentaire> commentaire = new ArrayList<>();

    @OneToMany(mappedBy = "createBy", cascade = CascadeType.ALL)
    private List<Sondage> sondages = new ArrayList<>();

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
    private List<DateSondee> dateSondee = new ArrayList<>();

    public Participant(Long participantId, String nom, String prenom) {
        this.participantId = participantId;
        this.nom = nom;
        this.prenom = prenom;
    }


    @Override
    public String toString() {
        return "Participant{" + "participantId=" + participantId + ", nom='" + nom + '\'' + ", prenom='" + prenom + '\'' + '}';
    }
}
