package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondee;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.DateSondeeRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Service pour la gestion des entités DateSondee.
 * Permet de créer des réponses (choix de dates) pour un sondage et de déterminer les meilleures dates.
 */
@Service
public class DateSondeeService {

    // Repository pour interagir avec les entités DateSondee dans la base de données
    private final DateSondeeRepository repository;
    // Service pour la gestion des entités DateSondage
    private final DateSondageService sdate;
    // Service pour la gestion des entités Participant
    private final ParticipantService ps;

    /**
     * Constructeur avec injection des dépendances.
     * @param repository Le repository pour les entités DateSondee.
     * @param d Le service pour la gestion des dates associées à un sondage.
     * @param p Le service pour la gestion des participants.
     */
    public DateSondeeService(DateSondeeRepository repository, DateSondageService d, ParticipantService p) {
        this.repository = repository;
        this.sdate = d;
        this.ps = p;
    }

    /**
     * Crée une nouvelle réponse (DateSondee) pour un participant sur une date spécifique d'un sondage.
     * La date ne peut être associée que si le sondage n'est pas clôturé.
     * @param id L'identifiant de la date sondage.
     * @param participantId L'identifiant du participant.
     * @param dateSondee L'entité DateSondee à créer.
     * @return L'entité DateSondee nouvellement créée et sauvegardée, ou null si le sondage est clôturé.
     */
    public DateSondee create(Long id, Long participantId, DateSondee dateSondee) {
        // Récupère l'entité DateSondage par son ID
        DateSondage date = sdate.getById(id);
        // Vérifie si le sondage associé à la date n'est pas clôturé
        if (Boolean.FALSE.equals(date.getSondage().getCloture())) {
            // Associe la date sondage et le participant à la réponse
            dateSondee.setDateSondage(date);
            dateSondee.setParticipant(ps.getById(participantId));
            // Sauvegarde l'entité en base de données
            return repository.save(dateSondee);
        }
        // Retourne null si le sondage est clôturé
        return null;
    }

    /**
     * Récupère les meilleures dates d'un sondage où le plus grand nombre de participants est disponible.
     * @param id L'identifiant du sondage.
     * @return Une liste de dates correspondant aux meilleures options.
     */
    public List<Date> bestDate(Long id) {
        return repository.bestDate(id);
    }

    /**
     * Récupère les dates possibles d'un sondage en incluant les participants qui ont répondu "DISPONIBLE" ou "PEUTETRE".
     * @param id L'identifiant du sondage.
     * @return Une liste de dates correspondant aux meilleures options incluant les réponses potentielles.
     */
    public List<Date> maybeBestDate(Long id) {
        return repository.maybeBestDate(id);
    }
}
