package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "date_sondee", uniqueConstraints = {@UniqueConstraint(columnNames = {"date_sondage_id", "participant_id"})})
@Getter
@Setter
@NoArgsConstructor
public class DateSondee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dateSondeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "date_sondage_id")
    private DateSondage dateSondage = new DateSondage();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private Participant participant = new Participant();

    @Column(name = "choix", nullable = false)
    @Enumerated(EnumType.STRING)
    private Choix choix;


    public DateSondee(Long dateSondeeId, DateSondage dateSondage, Participant participant, Choix choix) {
        this.dateSondeeId = dateSondeeId;
        this.dateSondage = dateSondage;
        this.participant = participant;
        this.choix = choix;
    }
}
