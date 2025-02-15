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
 * Liste des dates du {@link Sondage}
 */
@Entity
@Table(name = "date_sondage", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "sondage_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DateSondage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "date_sondage_id")
    private Long dateSondageId;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sondage_id")
    private Sondage sondage = new Sondage();

    @OneToMany(mappedBy = "dateSondage", cascade = CascadeType.ALL)
    private List<DateSondee> dateSondee = new ArrayList<>();

}
