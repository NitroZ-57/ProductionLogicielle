package Intergration;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.MySurveyApplication;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.ParticipantRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MySurveyApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class ParticipantIntegrationTest {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ParticipantRepository participantRepository;

    @Test
    void testCreateParticipant() {
        // Given : Création d'un participant
        Participant participant = new Participant(null, "Alice", "Doe");


        // When : Sauvegarde via le service
        Participant savedParticipant = participantService.create(participant);

        // Then : Vérification de la persistance via le repository
        Participant retrievedParticipant = participantRepository.findById(savedParticipant.getParticipantId()).orElse(null);

        assertThat(retrievedParticipant).isNotNull();
        assertThat(retrievedParticipant.getNom()).isEqualTo("Alice");
        assertThat(retrievedParticipant.getPrenom()).isEqualTo("Doe");

    }
    @Test
    void testSaveParticipantWithNullNom() {
        Participant participant = new Participant(null,null, null);
        assertThrows(DataIntegrityViolationException.class, () -> participantRepository.save(participant));
    }

    @Test
    void testUpdateParticipant() {
        // Given : Création et sauvegarde d'un participant
        Participant participant = new Participant(null,"Jane", "Doe");
        Participant savedParticipant = participantService.create(participant);

        // When : Mise à jour du nom du participant
        savedParticipant.setNom("Janet");
        Participant updatedParticipant = participantService.update(savedParticipant.getParticipantId(),savedParticipant);

        // Then : Vérification que les modifications sont bien appliquées
        assertThat(updatedParticipant.getNom()).isEqualTo("Janet");
        assertThat(updatedParticipant.getPrenom()).isEqualTo("Doe");
    }

    //Test de mise à jour d'un participant avec un ID inexistant
    @Test
    void testUpdateNonExistentParticipant() {
        // Essayer de mettre à jour un participant qui n'existe pas
        Participant participant = new Participant(null, "Jane", "Doe");
        Participant updatedParticipant = participantService.update(999L, participant);

        // Vérifier que la mise à jour échoue et renvoie null
        assertThat(updatedParticipant).isNull();
    }

    /*
    @Test
    void testUpdateParticipantWithInvalidData() {
        // Créer un participant initial
        Participant participant = new Participant(null, "Mike", "Jordan");
        Participant savedParticipant = participantService.create(participant);  // Crée et sauvegarde le participant

        // Essayer de mettre à jour avec des données invalides (par exemple, un nom null)
        savedParticipant.setNom(null);

        // Vérifier si une exception de violation d'intégrité des données se produit lors de la mise à jour
        assertThrows(DataIntegrityViolationException.class, () -> {
            participantService.update(savedParticipant.getParticipantId(), savedParticipant);  // Mise à jour du participant
        });
    }
*/

    @Test
    void testDeleteParticipant() {
        // Given
        Participant participant = new Participant(null,"John", "Doe");
        Participant savedParticipant = participantService.create(participant);

        // When
        participantService.delete(savedParticipant.getParticipantId());

        // Then
        Participant retrievedParticipant = participantRepository.findById(savedParticipant.getParticipantId()).orElse(null);
        assertThat(retrievedParticipant).isNull(); // Vérifie que le participant a été supprimé
    }
    @Test
    void testDeleteNonExistentParticipant() {
        assertDoesNotThrow(() -> participantService.delete(999L));
    }
    @Test
    void testGetParticipantById() {
        // Given
        Participant participant = new Participant(null,"Mike", "Jordan");
        Participant savedParticipant = participantService.create(participant);

        // When
        Participant retrievedParticipant = participantRepository.findById(savedParticipant.getParticipantId()).orElse(null);

        //THen
        assertThat(retrievedParticipant).isNotNull();
        assertThat(retrievedParticipant.getParticipantId()).isEqualTo(savedParticipant.getParticipantId());
    }

    @Test
    void testGetAllParticipants() {
        // Given
        participantService.create(new Participant(null,"Alice", "Smith"));
        participantService.create(new Participant(null, "Bob", "Brown"));

        // When
        List<Participant> participants = participantService.getAll();

        // Then
        assertThat(participants).isNotEmpty();
        assertThat(participants.size()).isGreaterThanOrEqualTo(2);
    }


    //Ce test vérifie qu'une tentative de récupération d'un participant inexistant retourne bien null
    @Test
    void testGetParticipantByNonExistentId() {
        // When
        Participant retrievedParticipant = participantRepository.findById(999L).orElse(null);

        // Then
        assertThat(retrievedParticipant).isNull();
    }


}
