package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CommentaireDto {

    private Long commentaireId;
    private String commentaire;
    private Long participant;

}
