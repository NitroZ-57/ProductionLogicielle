package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos;

import java.util.Date;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class DateSondageDto {

    private Long dateSondageId;
    private Date date;

}
