package Unit;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.DateSondageRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.DateSondageService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.SondageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DateSondageUnitTest {

    @Mock
    private DateSondageRepository dateSondageRepository;

    @Mock
    private SondageService sondageService;

    @InjectMocks
    private DateSondageService dateSondageService;


    @Test
    void givenEmptyService_whenGetDateSondage_thenDateSondageIsNull() {
        // Given

        // When
        when(dateSondageRepository.getById(any(Long.class))).thenReturn(null);
        DateSondage date = dateSondageService.getById(1L);

        // Then
        verify(dateSondageRepository, times(1)).getById(any(Long.class));
        assertNull(date);
    }


    @Test
    void givenEmptyService_whenGetAllDateOfSondage_thenListEmpty() {
        // Given

        // When
        when(dateSondageRepository.getAllBySondage(1L)).thenReturn(new ArrayList<DateSondage>());
        List<DateSondage> d = dateSondageService.getBySondageId(1L);

        // Then
        verify(dateSondageRepository, times(1)).getAllBySondage(1L);
        assertEquals(0 , d.size());
    }


    @Test
    void givenEmptyService_whenDeleteADate_thenListEmpty() {
        // Given

        // When
        when(dateSondageRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        int res = dateSondageService.delete(1L);
        when(dateSondageRepository.getAllBySondage(1L)).thenReturn(new ArrayList<DateSondage>());
        List<DateSondage> d = dateSondageService.getBySondageId(1L);

        // Then
        verify(dateSondageRepository, times(1)).findById(any(Long.class));
        assertEquals(0 , d.size());
        assertEquals(0, res);
    }


    @Test
    void givenDateSondage_whenAddDateSondageToService_thenDateSondageExist() {
        // Given
        DateSondage dateSondage = mock(DateSondage.class);

        // When
        when(dateSondageRepository.save(dateSondage)).thenReturn(dateSondage);
        DateSondage d = dateSondageService.create(1L, dateSondage);

        // Then
        verify(dateSondageRepository, times(1)).save(dateSondage);
        assertEquals(dateSondage , d);
    }


    @Test
    void given3DateSondage_whenAddAllDateSondageToService_thenListSizeIs3() {
        // Given
        DateSondage dateSondage1 = mock(DateSondage.class);
        DateSondage dateSondage2 = mock(DateSondage.class);
        DateSondage dateSondage3 = mock(DateSondage.class);
        Long dateSondageID = 4L;

        // When
        when(dateSondageRepository.save(any(DateSondage.class))).thenReturn(dateSondage1);
        dateSondageService.create(dateSondageID, dateSondage1);
        dateSondageService.create(dateSondageID, dateSondage2);
        dateSondageService.create(dateSondageID, dateSondage3);

        // Then
        verify(dateSondageRepository, times(3)).save(any(DateSondage.class));
    }


    @Test
    void givenDateSondage_whenAddItInSondage_thenNotPresentInOtherSondage() {
        // Given
        DateSondage dateSondage = mock(DateSondage.class);
        Long insertID = 1L;

        // When
        when(dateSondageRepository.save(any(DateSondage.class))).thenReturn(dateSondage);
        dateSondageService.create(insertID, dateSondage);

        // Then
        Long otherID = 2L;
        when(dateSondageRepository.getAllBySondage(otherID)).thenReturn(new ArrayList<DateSondage>());
        List<DateSondage> d = dateSondageService.getBySondageId(otherID);
        assertEquals(0 , d.size());
    }


    @Test
    void givenDateSondage_whenAddItInSondage_thenIsInSondage() {
        // Given
        DateSondage dateSondage = mock(DateSondage.class);
        Long insertID = 1L;

        // When
        when(dateSondageRepository.save(any(DateSondage.class))).thenReturn(dateSondage);
        DateSondage date = dateSondageService.create(insertID, dateSondage);

        // Then
        when(dateSondageRepository.getAllBySondage(insertID)).thenReturn(List.of(date));
        List<DateSondage> d = dateSondageService.getBySondageId(insertID);
        assertEquals(1 , d.size());
        assertEquals(dateSondage , d.get(0));
    }


    @Test
    void givenServiceWithDateSondage_whenDeleteDateSondage_thenDateSondageNotExist() {
        // given
        DateSondage dateSondage = mock(DateSondage.class);
        when(dateSondageRepository.save(any(DateSondage.class))).thenReturn(dateSondage);
        DateSondage d = dateSondageService.create(1L, dateSondage);

        // when
        when(dateSondageRepository.findById(d.getDateSondageId())).thenReturn(Optional.of(d));
        int res = dateSondageService.delete(d.getDateSondageId());

        // then
        verify(dateSondageRepository, times(1)).deleteById(d.getDateSondageId());
        assertEquals(1, res);
        assertEquals(0, dateSondageRepository.findAll().size());
    }

}