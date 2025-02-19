package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.SondageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des sondages.
 * Cette classe permet de gérer les opérations CRUD sur les sondages.
 */
@Service
public class SondageService {

    // Repository pour interagir avec les sondages dans la base de données
    private final SondageRepository repository;

    // Service pour gérer les participants associés aux sondages
    private final ParticipantService participantService;

    /**
     * Constructeur avec injection des dépendances.
     * @param repository Le repository pour la gestion des sondages.
     * @param p Le service pour la gestion des participants.
     */
    public SondageService(SondageRepository repository, ParticipantService p) {
        this.repository = repository;
        this.participantService = p;
    }

    /**
     * Récupère un sondage par son identifiant.
     * @param id L'identifiant du sondage à récupérer.
     * @return Le sondage correspondant à l'identifiant.
     */
    public Sondage getById(Long id) {
        return repository.getById(id);
    }

    /**
     * Récupère tous les sondages enregistrés dans la base de données.
     * @return Une liste de tous les sondages.
     */
    public List<Sondage> getAll() {
        return repository.findAll();
    }

    /**
     * Crée un nouveau sondage dans la base de données.
     * Associe le sondage à un participant créateur.
     * @param idParticipant L'identifiant du participant qui crée le sondage.
     * @param sondage L'entité Sondage à sauvegarder.
     * @return Le sondage nouvellement créé et sauvegardé.
     */
    public Sondage create(Long idParticipant, Sondage sondage) {
        // Associe le sondage à son créateur
        sondage.setCreateBy(this.participantService.getById(idParticipant));
        return repository.save(sondage);
    }

    /**
     * Met à jour un sondage existant dans la base de données.
     * @param id L'identifiant du sondage à mettre à jour.
     * @param sondage Les nouvelles informations du sondage.
     * @return Le sondage mis à jour, ou null si le sondage n'existe pas.
     */
    public Sondage update(Long id, Sondage sondage) {
        // Vérifie si le sondage existe avant de procéder à la mise à jour
        if (repository.findById(id).isPresent()) {
            // Définit l'identifiant pour mettre à jour l'entité existante
            sondage.setSondageId(id);
            return repository.save(sondage);
        }
        // Retourne null si le sondage n'existe pas
        return null;
    }

    /**
     * Supprime un sondage de la base de données.
     * @param id L'identifiant du sondage à supprimer.
     * @return 1 si la suppression est réussie, 0 si le sondage n'existe pas.
     */
    public int delete(Long id) {
        // Vérifie si le sondage existe avant de le supprimer
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return 1;
        }
        // Retourne 0 si le sondage n'existe pas
        return 0;
    }
}
