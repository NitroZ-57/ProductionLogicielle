package Unit;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Commentaire;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.CommentaireRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.CommentaireService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.SondageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentaireUnitTest {

    @Mock
    private CommentaireRepository commentaireRepository;

    @Mock
    private SondageService sondageService;

    @Mock
    private ParticipantService participantService;

    @InjectMocks
    private CommentaireService commentaireService;


    @Test
    void givenEmptyService_whenGetCommentaireOfSondage_thenListIsEmpty() {
        // Given

        // When
        List<Commentaire> l = commentaireService.getBySondageId(1L);

        // Then
        assertEquals(0 , l.size());
    }


    @Test
    void givenEmptyService_whenDeleteCommentaire_thenNoDelete() {
        // Given

        // When
        int res = commentaireService.delete(1L);

        // Then
        assertEquals(0 , res);
    }


    @Test
    void givenCommentaire_whenAddInSondage_thenCommentaireExist() {
        // Given
        Commentaire commentaire = mock(Commentaire.class);

        // When
        when(commentaireRepository.save(any(Commentaire.class))).thenReturn(commentaire);
        Commentaire added = commentaireService.addCommantaire(1L, 1L, commentaire);

        // Then
        verify(commentaireRepository, times(1)).save(commentaire);
        assertEquals(commentaire , added);
    }


    @Test
    void givenServiceWithCommentaire_whenDeleteCommentaire_thenCommentaireNotExist() {
        // Given
        Long sondageID = 1L;
        Commentaire commentaire = mock(Commentaire.class);
        when(commentaireRepository.save(any(Commentaire.class))).thenReturn(commentaire);
        Commentaire added = commentaireService.addCommantaire(sondageID, 1L, commentaire);
        verify(commentaireRepository, times(1)).save(commentaire);

        // When
        when(commentaireRepository.findById(added.getCommentaireId())).thenReturn(Optional.of(commentaire));
        int res = commentaireService.delete(added.getCommentaireId());

        // Then
        List<Commentaire> l = commentaireService.getBySondageId(sondageID);
        verify(commentaireRepository, times(1)).deleteById(added.getCommentaireId());
        assertEquals(1, res);
        assertEquals(0 , l.size());
    }

    @Test
    void givenEmptyService_whenAdd3Commentaires_ThenAllAdded() {
        // Given
        Long sondageID = 1L;
        Commentaire commentaire1 = mock(Commentaire.class);
        Commentaire commentaire2 = mock(Commentaire.class);
        Commentaire commentaire3 = mock(Commentaire.class);

        // When
        when(commentaireRepository.save(any(Commentaire.class))).thenReturn(commentaire1);
        commentaireService.addCommantaire(sondageID, 1L, commentaire1);
        commentaireService.addCommantaire(sondageID, 1L, commentaire2);
        commentaireService.addCommantaire(sondageID, 1L, commentaire3);

        // Then
        verify(commentaireRepository, times(3)).save(any(Commentaire.class));
    }


    @Test
    void givenEmptyService_whenUpdateCommentaire_thenNothingIsDone() {
        // Given
        Commentaire commentaire = mock(Commentaire.class);

        // When
        Commentaire updated = commentaireService.update(1L, commentaire);

        // Then
        verify(commentaireRepository, times(1)).findById(any(Long.class));
        verify(commentaireRepository, times(0)).save(any(Commentaire.class));
        assertNull(updated);
    }


    @Test
    void givenServiceWithCommentaire_whenUpdateCommentaire_thenCommentaireUpdated() {
        // Given
        Long sondageID = 3L;
        Commentaire commentaire = mock(Commentaire.class);
        when(commentaireRepository.save(any(Commentaire.class))).thenReturn(commentaire);
        Commentaire added = commentaireService.addCommantaire(sondageID, 1L, commentaire);

        // When
        Commentaire c2 = mock(Commentaire.class);
        when(commentaireRepository.findById(added.getCommentaireId())).thenReturn(Optional.of(added));
        when(commentaireRepository.save(c2)).thenReturn(c2);
        Commentaire updated = commentaireService.update(added.getCommentaireId(), c2);

        // Then
        verify(commentaireRepository, times(2)).save(any(Commentaire.class));
        verify(commentaireRepository, times(1)).findById(added.getCommentaireId());
        assertEquals(updated.getCommentaireId(), c2.getCommentaireId());
    }

}
