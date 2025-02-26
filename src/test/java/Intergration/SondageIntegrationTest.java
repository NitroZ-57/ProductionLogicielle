package Intergration;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.MySurveyApplication;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.ParticipantRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.SondageRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.SondageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MySurveyApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class SondageIntegrationTest {
    @Autowired
    private SondageService sondageService;

    @Autowired
    private SondageRepository sondageRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Test
    void testCreateSondage() {
        // Given
        Participant participant = new Participant(null, "Alice", "BOB");
        participantRepository.save(participant);

        Sondage sondage = new Sondage();
        sondage.setNom("Test Sondage");

        //when
        Sondage createdSondage = sondageService.create(participant.getParticipantId(), sondage);

        // Then
        assertNotNull(createdSondage);
        assertEquals("Test Sondage", createdSondage.getNom());
        assertNotNull(createdSondage.getCreateBy());
        assertEquals(participant.getParticipantId(), createdSondage.getCreateBy().getParticipantId());

        // Vérification de la récupération
        Sondage retrievedSondage = sondageService.getById(createdSondage.getSondageId());
        assertNotNull(retrievedSondage);
        assertEquals("Test Sondage", retrievedSondage.getNom());
    }
    @Test
    void testUpdateSondage() {
        // Given
        Participant participant = new Participant(null, "Alice", "BOB");
        participantRepository.save(participant);

        Sondage sondage = new Sondage();
        sondage.setNom("Original Sondage");
        Sondage createdSondage = sondageService.create(participant.getParticipantId(), sondage);

        // When : Mise à jour du sondage
        createdSondage.setNom("Updated Sondage");
        Sondage updatedSondage = sondageService.update(createdSondage.getSondageId(), createdSondage);

        // Then
        assertNotNull(updatedSondage);
        assertEquals("Updated Sondage", updatedSondage.getNom());
    }

    @Test
    void testDeleteSondage() {
        // Given
        Participant participant = new Participant(null, "Alice", "BOB");

        participantRepository.save(participant);

        Sondage sondage = new Sondage();
        sondage.setNom("Delete Sondage");
        Sondage createdSondage = sondageService.create(participant.getParticipantId(), sondage);

        // When : Suppression du sondage
        int result = sondageService.delete(createdSondage.getSondageId());
        assertEquals(1, result);

        // Then : Vérification de la suppression
        assertThrows(JpaObjectRetrievalFailureException.class,
                () -> sondageService.getById(createdSondage.getSondageId()));

    }

    @Test
    void testGetAllSondages() {
        // Given
        Participant participant = new Participant(null, "Alice", "BOB");
        participantRepository.save(participant);

        // Création de plusieurs sondages
        Sondage sondage1 = new Sondage();
        sondage1.setNom("Sondage 1");

        Sondage sondage2 = new Sondage();
        sondage2.setNom("Sondage 2");
        sondageService.create(participant.getParticipantId(), sondage1);
        sondageService.create(participant.getParticipantId(), sondage2);

        // When
        List<Sondage> sondages = sondageService.getAll();

        // Then
        assertTrue(sondages.size() >= 2);
    }


}
