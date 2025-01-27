package Intergration;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.MySurveyApplication;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.ParticipantRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MySurveyApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class ParticipantServiceIntegrationTest {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ParticipantRepository participantRepository;

    @Test
    void testCreateAndRetrieveParticipant() {
        // Création d'un participant
        Participant participant = new Participant(new Long(1), "John", "Doe");

        Participant savedParticipant = participantService.create(participant);

        assertNotNull(savedParticipant);
        assertNotNull(savedParticipant.getParticipantId());

        // Vérification qu'il est récupérable
        Participant retrievedParticipant = participantService.getById(savedParticipant.getParticipantId());
        assertEquals(new Long(1), retrievedParticipant.getParticipantId());
    }


}
