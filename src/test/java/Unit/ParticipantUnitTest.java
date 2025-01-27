package Unit;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParticipantUnitTest {

    @Test
    void testNameParticipant() {
        Participant participant = new Participant(new Long(1), "John", "Doe");
        assertEquals("John", participant.getNom(), "Nom doit être John");
    }
}
