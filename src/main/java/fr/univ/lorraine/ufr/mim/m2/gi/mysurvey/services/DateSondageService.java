package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.DateSondageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des entités DateSondage.
 * Fournit des méthodes pour récupérer, créer et supprimer des dates associées à un sondage.
 */
@Service
public class DateSondageService {

    // Repository pour interagir avec la base de données
    private final DateSondageRepository repository;
    // Service pour gérer les sondages
    private final SondageService sondageService;

    /**
     * Constructeur avec injection des dépendances.
     * @param repository Le repository pour les entités DateSondage.
     * @param sondageService Le service pour la gestion des sondages.
     */
    public DateSondageService(DateSondageRepository repository, SondageService sondageService) {
        this.repository = repository;
        this.sondageService = sondageService;
    }

    /**
     * Récupère une entité DateSondage par son identifiant.
     * @param id L'identifiant de la date sondage.
     * @return L'entité DateSondage correspondante.
     */
    public DateSondage getById(Long id) {
        return repository.getById(id);
    }

    /**
     * Récupère toutes les dates associées à un sondage spécifique.
     * @param sondageId L'identifiant du sondage.
     * @return Une liste d'entités DateSondage associées au sondage.
     */
    public List<DateSondage> getBySondageId(Long sondageId) {
        return repository.getAllBySondage(sondageId);
    }

    /**
     * Crée une nouvelle date associée à un sondage.
     * @param id L'identifiant du sondage auquel la date doit être liée.
     * @param date L'entité DateSondage à créer.
     * @return L'entité DateSondage nouvellement créée et sauvegardée.
     */
    public DateSondage create(Long id, DateSondage date) {
        // Associe la date au sondage correspondant
        date.setSondage(sondageService.getById(id));
        // Sauvegarde la date en base de données
        return repository.save(date);
    }

    /**
     * Supprime une date sondage par son identifiant.
     * @param id L'identifiant de la date sondage à supprimer.
     * @return 1 si la suppression a réussi, 0 sinon.
     */
    public int delete(Long id) {
        // Vérifie si l'entité existe avant de la supprimer
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return 1;
        }
        // Retourne 0 si l'entité n'existe pas
        return 0;
    }
}
