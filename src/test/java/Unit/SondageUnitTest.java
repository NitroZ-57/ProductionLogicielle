package Unit;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.SondageRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.SondageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SondageUnitTest {



    @Mock
    private SondageRepository sondageRepository;

    @Mock
    private ParticipantService participantService;

    @InjectMocks
    private SondageService sondageService;



    @Test
    void givenEmptyService_whenUpdateASondage_thenServiceAlwaysEmpty() {
        // Given
        Sondage s = mock(Sondage.class);

        // When
        sondageService.update(22L, s);

        // Then
        verify(sondageRepository, times(0)).save(any(Sondage.class)); // Vérifie que save n'a pas été appelé
    }

    @Test
    void givenEmptyService_whenDeleteRandomID_thenServiceAlwaysEmpty() {
        // Given

        // When
        int res = sondageService.delete(22L);

        // Then
        verify(sondageRepository, times(0)).deleteById(any()); // Vérifie que delete n'a pas été appelé
        assertEquals(0, res);
    }

    @Test
    void givenEmptyService_whenGetRandomID_thenExceptionThrown() {
        // Given

        // When
        when(sondageRepository.getById(any())).thenReturn(null);
        Sondage s = sondageService.getById(42L);

        // Then
        verify(sondageRepository, times(1)).getById(any());
        assertNull(s);
    }

    @Test
    void givenSondage_whenSaveSondage_thenSondageExist() {
        // Given
        Sondage sondage = mock(Sondage.class);

        // When
        when(sondageRepository.save(sondage)).thenReturn(sondage);
        Sondage s = sondageService.create(1L, sondage);

        // Then
        verify(sondageRepository, times(1)).save(sondage); // Vérifie que delete n'a pas été appelé
        assertEquals(sondage, s);
    }

    @Test
    void givenServiceWithSondage_whenDeleteSondageOfService_thenSondageDeleted() {
        // Given
        Sondage sondage = mock(Sondage.class);
        when(sondageRepository.save(sondage)).thenReturn(sondage);
        Sondage s = sondageService.create(1L, sondage);

        // When
        when(sondageRepository.findById(s.getSondageId())).thenReturn(Optional.of(s));
        int res = sondageService.delete(s.getSondageId());

        // Then
        verify(sondageRepository, times(1)).deleteById(s.getSondageId()); // Vérifie que deleteById a été appelé une fois
        assertEquals(1, res);
    }

    @Test
    void givenThreeSondagesAndEmptyService_whenAddAllSondages_thenSizeOfSondagesEqualsThree() {
        // Given
        Sondage sondage1 = mock(Sondage.class);
        Sondage sondage2 = mock(Sondage.class);
        Sondage sondage3 = mock(Sondage.class);

        // When
        when(sondageRepository.save(any(Sondage.class))).thenReturn(sondage1);

        sondageService.create(1L, sondage1);
        sondageService.create(1L, sondage2);
        sondageService.create(1L, sondage3);

        verify(sondageRepository, times(3)).save(any(Sondage.class));
    }

    @Test
    void givenServiceWithSondage_whenUpdateSondageInService_thenSondageIsModified() {
        // Given
        Long participantID = 2L;
        Sondage sondage1 = mock(Sondage.class);
        when(sondageRepository.save(sondage1)).thenReturn(sondage1);
        Sondage s1 = sondageService.create(participantID, sondage1);

        // When
        Sondage sondage2 = mock(Sondage.class);
        when(sondageRepository.findById(s1.getSondageId())).thenReturn(Optional.of(sondage1));
        when(sondageRepository.save(sondage2)).thenReturn(sondage2);
        sondageService.update(s1.getSondageId(), sondage2);

        // Then
        verify(sondageRepository, times(2)).save(any(Sondage.class));
        when(sondageRepository.getById(s1.getSondageId())).thenReturn(sondage2);
        assertNotEquals(sondage1, sondageService.getById(s1.getSondageId()));
        assertEquals(sondage2, sondageService.getById(s1.getSondageId()));

    }


}
