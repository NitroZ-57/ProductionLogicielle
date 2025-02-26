package Unit;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondee;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.DateSondageRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.DateSondeeRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.DateSondageService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.DateSondeeService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.SondageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DateSondeeUnitTest {

    @Mock
    private DateSondeeRepository dateSondeeRepository;

    @Mock
    private DateSondageService dateSondageService;

    @Mock
    private ParticipantService participantService;

    @InjectMocks
    private DateSondeeService dateSondeeService;


    @Test
    void givenEmptyService_whenAddDateSondeeWithNoCloture_thenDateSondeeSaved() {
        // Given
        DateSondee d = mock(DateSondee.class);
        Sondage s = new Sondage();
        s.setCloture(Boolean.FALSE);
        DateSondage dateSondage = new DateSondage(1L, new Date(), s, new ArrayList<>());
        when(dateSondageService.getById(any(Long.class))).thenReturn(dateSondage);

        // When
        when(dateSondeeRepository.save(d)).thenReturn(d);
        DateSondee date = dateSondeeService.create(1L, 2L, d);

        // Then
        verify(dateSondeeRepository, times(1)).save(d);
        assertEquals(d, date);
    }


    @Test
    void givenEmptyService_whenAddDateSondeeWithCloture_thenDateSondeeNotSaved() {
        // Given
        DateSondee d = mock(DateSondee.class);
        Sondage s = new Sondage();
        s.setCloture(Boolean.TRUE);
        DateSondage dateSondage = new DateSondage(1L, new Date(), s, new ArrayList<>());
        when(dateSondageService.getById(any(Long.class))).thenReturn(dateSondage);

        // When
        DateSondee date = dateSondeeService.create(1L, 2L, d);

        // Then
        verify(dateSondeeRepository, times(0)).save(d);
        assertNull(date);
    }


    @Test
    void given_whenGetBestDate_thenRepositoryCalled() {
        // Given

        // When
        dateSondeeService.bestDate(1L);

        // Then
        verify(dateSondeeRepository, times(1)).bestDate(any(Long.class));
    }


    @Test
    void given_whenGetMaybeBestDate_thenRepositoryCalled() {
        // Given

        // When
        dateSondeeService.maybeBestDate(1L);

        // Then
        verify(dateSondeeRepository, times(1)).maybeBestDate(any(Long.class));
    }


}