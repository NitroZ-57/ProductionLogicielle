package Intergration;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.MySurveyApplication;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Choix;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondee;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.DateSondageRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.DateSondeeRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.ParticipantRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.DateSondageService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.DateSondeeService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MySurveyApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class DateSondeeIntegrationTest {
    @Autowired
    private DateSondeeService dateSondeeService;

    @Autowired
    private DateSondeeRepository dateSondeeRepository;

    @Autowired
    private DateSondageRepository dateSondageRepository;

    @Autowired
    private DateSondageService dateSondageService;

    @Autowired
    private ParticipantService participantService;

    private DateSondage sondage;
    private Participant participant;

    @BeforeEach
    void setUp() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        sondage = new DateSondage();
        sondage.setDate(formatter.parse("2025-02-14"));
        sondage = dateSondageRepository.save(sondage);
        participant = participantService.create(new Participant(null, "Alice", "Doe"));


    }

    @Test
    void testCreateDateSondeeSuccessfully() {
        // Given
        sondage.getSondage().setCloture(false);
        DateSondee dateSondee = new DateSondee();
        dateSondee.setChoix(Choix.DISPONIBLE);
        // When
        DateSondee createdDateSondee = dateSondeeService.create(sondage.getDateSondageId(), participant.getParticipantId(), dateSondee);

        // Then
        assertNotNull(createdDateSondee, "DateSondee should be created successfully");
        assertEquals(Choix.DISPONIBLE, createdDateSondee.getChoix());
        assertEquals(participant.getParticipantId(), createdDateSondee.getParticipant().getParticipantId());
        assertEquals(sondage.getDateSondageId(), createdDateSondee.getDateSondage().getDateSondageId());
    }

    @Test
    void testCreateDateSondeeWithClosedSondage() {
        // Given
        sondage.getSondage().setCloture(true);
        DateSondee dateSondee = new DateSondee();
        dateSondee.setChoix(Choix.INDISPONIBLE);

        //When
        DateSondee result = dateSondeeService.create(sondage.getDateSondageId(), participant.getParticipantId(), dateSondee);
        // Then
        assertNull(result, "DateSondee should not be created when the sondage is closed");
    }

    @Test
    void testRepositorySaveAndFind() {
        // Given
        DateSondee dateSondee = new DateSondee();
        dateSondee.setChoix(Choix.PEUTETRE);
        dateSondee.setParticipant(participant);
        dateSondee.setDateSondage(sondage);
        // When
        DateSondee savedDateSondee = dateSondeeRepository.save(dateSondee);
        // Then
        assertNotNull(savedDateSondee.getDateSondeeId(), "Saved DateSondee should have an ID");
        assertEquals(Choix.PEUTETRE, savedDateSondee.getChoix());
    }

}
