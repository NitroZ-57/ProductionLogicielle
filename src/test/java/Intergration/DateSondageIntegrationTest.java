package Intergration;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.MySurveyApplication;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.DateSondageRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.ParticipantRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.SondageRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.DateSondageService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.SondageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;


import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MySurveyApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class DateSondageIntegrationTest {
    @Autowired
    private DateSondageService dateSondageService;

    @Autowired
    private SondageService sondageService;

    @Autowired
    private SondageRepository sondageRepository;

    @Autowired
    private DateSondageRepository dateSondageRepository;

    private Sondage sondage;

    @Autowired
    private ParticipantRepository participantRepository;


    @BeforeEach
    void setup() {
        Participant participant = new Participant(null, "Alice", "BOB");
        participantRepository.save(participant);

        sondage = new Sondage();
        sondage.setNom("Sondage Test");
        sondage.setCreateBy(participant);
        sondage = sondageRepository.save(sondage);
    }

    @Test
    void testCreateDateSondage() {
        DateSondage dateSondage = new DateSondage();
        dateSondage.setDate(new Date());

        DateSondage createdDateSondage = dateSondageService.create(sondage.getSondageId(), dateSondage);
        assertNotNull(createdDateSondage.getDateSondageId());
        assertEquals(sondage.getSondageId(), createdDateSondage.getSondage().getSondageId());
    }
    @Test
    void testGetById() {
        DateSondage dateSondage = new DateSondage();
        dateSondage.setDate(new Date());

        DateSondage savedDateSondage = dateSondageService.create(sondage.getSondageId(), dateSondage);

        DateSondage foundDateSondage = dateSondageService.getById(savedDateSondage.getDateSondageId());
        assertNotNull(foundDateSondage);
        assertEquals(savedDateSondage.getDateSondageId(), foundDateSondage.getDateSondageId());
    }

    @Test
    void testGetBySondageId() {
        DateSondage dateSondage1 = new DateSondage();
        dateSondage1.setDate(new Date(System.currentTimeMillis()));

        DateSondage dateSondage2 = new DateSondage();
        dateSondage2.setDate(new Date(System.currentTimeMillis() + 86400000));

        dateSondageService.create(sondage.getSondageId(), dateSondage1);
        dateSondageService.create(sondage.getSondageId(), dateSondage2);

        List<DateSondage> dateSondages = dateSondageService.getBySondageId(sondage.getSondageId());
        assertEquals(2, dateSondages.size());
    }

    @Test
    void testDeleteDateSondage() {
        DateSondage dateSondage = new DateSondage();
        dateSondage.setDate(new Date());

        DateSondage savedDateSondage = dateSondageService.create(sondage.getSondageId(), dateSondage);

        int result = dateSondageService.delete(savedDateSondage.getDateSondageId());
        assertEquals(1, result);

        assertFalse(dateSondageRepository.findById(savedDateSondage.getDateSondageId()).isPresent());
    }

    @Test
    void testDeleteNonExistentDateSondage() {
        int result = dateSondageService.delete(999L);
        assertEquals(0, result);
    }
}
