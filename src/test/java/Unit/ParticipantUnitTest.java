package Unit;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.ParticipantDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.ParticipantRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipantUnitTest {

    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private ParticipantService participantService;


    @Test
    void givenEmptyService_whenUpdateAParticipant_thenServiceAlwaysEmpty() {
        // Given
        Participant p = mock(Participant.class);

        // When
        participantService.update(22L, p);

        // Then
        verify(participantRepository, times(0)).save(any(Participant.class)); // Vérifie que save n'a pas été appelé
}

    @Test
    void givenEmptyService_whenRandomID_thenServiceAlwaysEmpty() {
        // Given

        // When
        participantService.delete(22L);

        // Then
        verify(participantRepository, times(0)).deleteById(any()); // Vérifie que delete n'a pas été appelé
    }

    @Test
    void givenEmptyService_whenGetRandomID_thenExceptionThrown() {
        // Given

        // When
        when(participantRepository.getById(any())).thenReturn(null);
        participantService.getById(42L);

        // Then
        verify(participantRepository, times(0)).delete(any());
    }

    @Test
    void givenParticipant_whenSaveParticipant_thenParticipantExist() {
        // Given
        Participant participant = mock(Participant.class);

        // When
        when(participantRepository.save(any(Participant.class))).thenReturn(participant);
        Participant created = participantService.create(participant);

        // Then
        verify(participantRepository, times(1)).save(participant); // Vérifie que delete n'a pas été appelé
        assertEquals(participant, created);
    }

    @Test
    void givenServiceWithParticipant_whenDeleteParticipantOfService_thenExceptionThrown() {
        // Given
        Participant participant = mock(Participant.class);
        when(participantRepository.save(any(Participant.class))).thenReturn(participant);
        Long id = participantService.create(participant).getParticipantId();


        // When
        when(participantRepository.findById(id)).thenReturn(Optional.of(participant));
        participantService.delete(id);

        // Then
        verify(participantRepository, times(1)).deleteById(id); // Vérifie que deleteById a été appelé une fois
        assertEquals(0, participantService.getAll().size());
    }

    @Test
    void givenThreeParticipantsAndEmptyService_whenAddAllParticipants_thenSizeOfParticipantEqualsThree() {
        // Given
        Participant participant1 = mock(Participant.class);
        Participant participant2 = mock(Participant.class);
        Participant participant3 = mock(Participant.class);

        // When
        when(participantRepository.save(participant1)).thenReturn(participant1);
        when(participantRepository.save(participant2)).thenReturn(participant2);
        when(participantRepository.save(participant3)).thenReturn(participant3);

        participantService.create(participant1);
        participantService.create(participant2);
        participantService.create(participant3);

        verify(participantRepository, times(3)).save(any(Participant.class));
    }

    @Test
    void givenServiceWithParticipant_whenUpdateParticipantInService_thenParticipantIsModified() {
        // Given
        Participant participant = new Participant(3L, "Nom", "Prenom");
        when(participantRepository.save(any(Participant.class))).thenReturn(participant);
        Long id = participantService.create(participant).getParticipantId();

        // When
        String nouveauNom = "nouveauNom";
        Participant newParticipant = new Participant(3L, nouveauNom, "User");
        when(participantRepository.findById(id)).thenReturn(Optional.of(participant));
        when(participantRepository.save(newParticipant)).thenReturn(newParticipant);
        participantService.update(newParticipant.getParticipantId(), newParticipant);

        // Then
        verify(participantRepository, times(2)).save(any(Participant.class));
        when(participantRepository.getById(id)).thenReturn(newParticipant);
        assertNotEquals(participant, participantService.getById(id));
        assertEquals(newParticipant, participantService.getById(id));

    }

    // MODEL

    @Test
    void givenParticipant_whenInstancied_thenCorrectsFields() {
        // Given
        Long id = 3L;
        String nom = "Machin";
        String prenom = "Truc";

        // When
        Participant participant = new Participant(id, nom, prenom);

        // Then
        assertEquals(id, participant.getParticipantId());
        assertEquals(nom, participant.getNom());
        assertEquals(prenom, participant.getPrenom());
    }


    @Test
    void givenParticipant_whenToString_thenResultCorrect() {
        // Given
        Long id = 3L;
        String nom = "Machin";
        String prenom = "Truc";
        String attendu = "Participant{" + "participantId=" + id + ", nom='" + nom + '\'' + ", prenom='" + prenom + '\'' + '}';

        // When
        Participant participant = new Participant(id, nom, prenom);
        String s = participant.toString();

        // Then
        assertEquals(attendu, s);
    }

    @Test
    void givenParticipantDTO_whenToString_thenResultCorrect() {
        // Given
        String attendu = "ParticipantDto{" + "participantId=" + null + ", nom='" + null + '\'' + ", prenom='" + null + '\'' + '}';

        // When
        ParticipantDto participant = new ParticipantDto();
        String s = participant.toString();

        // Then
        assertEquals(attendu, s);
    }
}
