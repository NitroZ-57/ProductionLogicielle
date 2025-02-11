package Intergration;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.MySurveyApplication;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Commentaire;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.CommentaireRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.ParticipantRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.SondageRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.CommentaireService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = MySurveyApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class CommentaireIntegrationTest {
    @Autowired
    private CommentaireService commentaireService;
    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private SondageRepository sondageRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Test
    void testAddCommentaire() throws Exception {
        // Création d'un sondage et d'un participant pour la simulation
        Sondage sondage = new Sondage();
        sondage.setNom("Sondage de test");
        sondageRepository.save(sondage);

        Participant participant = new Participant(null,"alice","bob");
        participantRepository.save(participant);

        Commentaire commentaire = new Commentaire();
        commentaire.setCommentaire("Test commentaire");
        commentaire.setSondage(sondage);
        commentaire.setParticipant(participant);

        // Appeler la méthode du service pour ajouter le commentaire
        Commentaire savedCommentaire = commentaireService.addCommantaire(sondage.getSondageId(), participant.getParticipantId(), commentaire);

        // Vérifier que le commentaire est bien ajouté en base
        assertNotNull(savedCommentaire);
        assertEquals("Test commentaire", savedCommentaire.getCommentaire());
        assertEquals(sondage.getSondageId(), savedCommentaire.getSondage().getSondageId());
        assertEquals(participant.getParticipantId(), savedCommentaire.getParticipant().getParticipantId());

        // Optionnel : Vérifier que le commentaire a bien été enregistré dans la base de données
        Commentaire commentaireInDb = commentaireRepository.findById(savedCommentaire.getCommentaireId()).orElse(null);
        assertNotNull(commentaireInDb);
    }

    @Test
    void testUpdateCommentaire() throws Exception {
        // Création d'un sondage et d'un participant pour la simulation
        Sondage sondage = new Sondage();
        sondage.setNom("Sondage de test");
        sondageRepository.save(sondage);

        Participant participant = new Participant(null,"alice","bob");
        participantRepository.save(participant);

        // Création du commentaire initial
        Commentaire commentaire = new Commentaire();
        commentaire.setCommentaire("Ancien commentaire");
        commentaire.setSondage(sondage);
        commentaire.setParticipant(participant);
        commentaireRepository.save(commentaire);

        // Mise à jour du commentaire
        commentaire.setCommentaire("Nouveau commentaire");
        Commentaire updatedCommentaire = commentaireService.update(commentaire.getCommentaireId(), commentaire);

        // Vérifier que le commentaire a bien été mis à jour
        assertNotNull(updatedCommentaire);
        assertEquals("Nouveau commentaire", updatedCommentaire.getCommentaire());

        // Vérifier que le commentaire mis à jour est bien dans la base de données
        Commentaire commentaireInDb = commentaireRepository.findById(updatedCommentaire.getCommentaireId()).orElse(null);
        assertNotNull(commentaireInDb);
        assertEquals("Nouveau commentaire", commentaireInDb.getCommentaire());
    }

    @Test
    void testDeleteCommentaire() throws Exception {
        // Création d'un sondage et d'un participant pour la simulation
        Sondage sondage = new Sondage();
        sondage.setNom("Sondage de test");
        sondageRepository.save(sondage);

        Participant participant = new Participant(null,"alice","bob");
        participantRepository.save(participant);

        // Création du commentaire
        Commentaire commentaire = new Commentaire();
        commentaire.setCommentaire("Commentaire à supprimer");
        commentaire.setSondage(sondage);
        commentaire.setParticipant(participant);
        commentaireRepository.save(commentaire);

        // Suppression du commentaire
        commentaireService.delete(commentaire.getCommentaireId());

        // Vérifier que le commentaire a bien été supprimé
        Commentaire commentaireInDb = commentaireRepository.findById(commentaire.getCommentaireId()).orElse(null);
        assertNull(commentaireInDb);
    }

    @Test
    void testGetBySondageId() throws Exception {
        // Création d'un sondage et d'un participant pour la simulation
        Participant participant = new Participant(null,"alice","bob");
        participantRepository.save(participant);

        Sondage sondage = new Sondage();
        sondage.setNom("Sondage de test");
        sondage.setCreateBy(participant);
        sondageRepository.save(sondage);


        // Création de plusieurs commentaires associés à ce sondage
        Commentaire commentaire1 = new Commentaire();
        commentaire1.setCommentaire("Premier commentaire");
        commentaire1.setSondage(sondage);
        commentaire1.setParticipant(participant);
        commentaireRepository.save(commentaire1);

        Commentaire commentaire2 = new Commentaire();
        commentaire2.setCommentaire("Deuxième commentaire");
        commentaire2.setSondage(sondage);
        commentaire2.setParticipant(participant);
        commentaireRepository.save(commentaire2);

        // Appeler la méthode pour récupérer les commentaires par sondageId
        List<Commentaire> commentaires = commentaireService.getBySondageId(sondage.getSondageId());

        // Vérifier que les commentaires récupérés sont bien ceux associés au sondage
        assertNotNull(commentaires);
        assertEquals(2, commentaires.size());
        assertTrue(commentaires.stream().anyMatch(c -> c.getCommentaire().equals("Premier commentaire")));
        assertTrue(commentaires.stream().anyMatch(c -> c.getCommentaire().equals("Deuxième commentaire")));

        // Vérifier que les commentaires ont bien le bon ID de sondage
        commentaires.forEach(c -> assertEquals(sondage.getSondageId(), c.getSondage().getSondageId()));
    }

}


