package Unit;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.MySurveyApplication;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.ParticipantRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MySurveyApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class ParticipantUnitTest {

    @Autowired
    private ParticipantRepository participantRepository;


    @Autowired
    private ParticipantService participantService;


    @Test
    void givenEmptyService_whenUpdateAParticipant_thenServiceAlwaysEmpty() {
        // Given
        assertEquals(0, participantService.getAll().size(), "Erreur le service n'est pas vide");

        // When
        participantService.update(22L, new Participant(22L, "Update", "Fail"));

        // Then
        assertEquals(0, participantService.getAll().size(), "Erreur un ajout a été fait dans le service");
    }


    @Test
    void givenEmptyService_whenDeleteAParticipant_thenServiceAlwaysEmpty() {
        // Given
        assertEquals(0, participantService.getAll().size(), "Erreur le service n'est pas vide");

        // When
        participantService.delete(22L);

        // Then
        assertEquals(0, participantService.getAll().size(), "Erreur un ajout a été fait dans le service");
    }



    @Test
    void givenEmptyService_whenGetAParticipant_thenExceptionThrown() {
        // Given
        assertEquals(0, participantService.getAll().size(), "Erreur le service n'est pas vide");

        // When
        Exception exception = assertThrows(Exception.class, () -> {
            Participant p = participantService.getById(42L);
            assertNull(p);
        });

        // Then
        assertEquals("EntityNotFoundException", exception.getClass().getSimpleName());
    }


    @Test
    void givenParticipant_whenAddParticipantToService_thenParticipantExist() {
        // Given
        Participant participant = new Participant(1L, "Test", "Prenom");

        // When
        Participant p = participantService.create(participant);

        // Then
        assertNotNull(p, "Participant pas ajouté au service");
        assertNotNull(participantService.getById(p.getParticipantId()), "Participant pas retrouvé dans le service");
    }



    @Test
    void givenServiceWithParticipant_whenDeleteParticipantOfService_thenExceptionThrown() {
        // Given
        Participant participant = new Participant(3L, "Deleted", "User");
        Participant p = participantService.create(participant);
        assertNotNull(p, "Impossible d'ajouter le participant");

        // When
        participantService.delete(p.getParticipantId());

        // Then
        Exception exception = assertThrows(Exception.class, () -> {
            assertNull(participantService.getById(p.getParticipantId()), "Impossible de retrouver le participant à supprimer");
        });
        assertEquals("JpaObjectRetrievalFailureException", exception.getClass().getSimpleName());
    }


    @Test
    void givenTreeParticipantsAndEmptyService_whenAddAllParticipants_thenSizeOfParticipantEqualsTree() {
        // Given
        assertEquals(0, participantService.getAll().size(), "Le service n'est pas vide");
        Participant participant1 = new Participant(11L, "Number One", "User");
        Participant participant2 = new Participant(12L, "Number Two", "User");
        Participant participant3 = new Participant(13L, "Number Tree", "User");


        // When
        assertNotNull(participantService.create(participant1), "Impossible d'ajouter le participant 1");
        assertNotNull(participantService.create(participant2), "Impossible d'ajouter le participant 2");
        assertNotNull(participantService.create(participant3), "Impossible d'ajouter le participant 3");

        // Then
        assertEquals(3, participantService.getAll().size(), "La taille du service n'est pas bon");
    }


    @Test
    void givenServiceWithParticipant_whenUpdateParticipantInService_thenParticipantIsModified() {
        // Given
        String ancienNom = "Old";
        Participant oldParticipant = new Participant(3L, ancienNom, "User");
        Participant p = participantService.create(oldParticipant);
        assertNotNull(p, "Impossible d'ajouter le participant");
        Long id = p.getParticipantId();

        // When
        String nouveauNom = "New";
        Participant newParticipant = new Participant(id, nouveauNom, "User");
        assertNotNull(participantService.update(id, newParticipant), "Erreur lors de l'update");

        // Then
        assertEquals(nouveauNom, participantService.getById(id).getNom(), "Le participant n'a pas le nouveau nom");
    }

}
