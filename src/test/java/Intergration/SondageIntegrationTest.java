package Intergration;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.MySurveyApplication;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.ParticipantRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.SondageRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.SondageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.annotation.DirtiesContext;
import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MySurveyApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class SondageIntegrationTest {
    @Autowired
    private SondageService sondageService;

    @Autowired
    private SondageRepository sondageRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Test
    void testCreateSondage() {
        Participant participant = new Participant(null, "Alice", "BOB");
        participantRepository.save(participant);

        Sondage sondage = new Sondage();
        sondage.setNom("Test Sondage");

        Sondage createdSondage = sondageService.create(participant.getParticipantId(), sondage);

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
        Participant participant = new Participant(null, "Alice", "BOB");
        participantRepository.save(participant);

        Sondage sondage = new Sondage();
        sondage.setNom("Original Sondage");
        Sondage createdSondage = sondageService.create(participant.getParticipantId(), sondage);

        // Mise à jour du sondage
        createdSondage.setNom("Updated Sondage");
        Sondage updatedSondage = sondageService.update(createdSondage.getSondageId(), createdSondage);

        assertNotNull(updatedSondage);
        assertEquals("Updated Sondage", updatedSondage.getNom());
    }

    @Test
    void testDeleteSondage() {
        Participant participant = new Participant(null, "Alice", "BOB");

        participantRepository.save(participant);

        Sondage sondage = new Sondage();
        sondage.setNom("Delete Sondage");
        Sondage createdSondage = sondageService.create(participant.getParticipantId(), sondage);

        // Suppression du sondage
        int result = sondageService.delete(createdSondage.getSondageId());
        assertEquals(1, result);

        // Vérification de la suppression
        assertThrows(JpaObjectRetrievalFailureException.class, () -> {
            sondageService.getById(createdSondage.getSondageId());
        });
    }

    @Test
    void testGetAllSondages() {
        Participant participant = new Participant(null, "Alice", "BOB");
        participantRepository.save(participant);

        // Création de plusieurs sondages
        Sondage sondage1 = new Sondage();
        sondage1.setNom("Sondage 1");

        Sondage sondage2 = new Sondage();
        sondage2.setNom("Sondage 2");
        sondageService.create(participant.getParticipantId(), sondage1);
        sondageService.create(participant.getParticipantId(), sondage2);

        List<Sondage> sondages = sondageService.getAll();

        assertTrue(sondages.size() >= 2);
    }


}
