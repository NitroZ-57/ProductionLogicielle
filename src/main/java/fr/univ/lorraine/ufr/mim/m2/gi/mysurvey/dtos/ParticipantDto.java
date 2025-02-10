package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ParticipantDto {

    private Long participantId;
    private String nom;
    private String prenom;

    @Override
    public String toString() {
        return "ParticipantDto{" + "participantId=" + participantId + ", nom='" + nom + '\'' + ", prenom='" + prenom + '\'' + '}';
    }
}
