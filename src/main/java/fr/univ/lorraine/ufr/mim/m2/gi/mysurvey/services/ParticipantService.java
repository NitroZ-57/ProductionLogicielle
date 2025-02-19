package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des entités Participant.
 * Ce service permet de gérer les opérations CRUD sur les participants.
 */
@Service
public class ParticipantService {

    // Repository pour interagir avec les entités Participant dans la base de données
    private final ParticipantRepository repository;

    /**
     * Constructeur avec injection du repository ParticipantRepository.
     * @param repository Le repository pour la gestion des entités Participant.
     */
    public ParticipantService(ParticipantRepository repository) {
        this.repository = repository;
    }

    /**
     * Récupère un participant par son identifiant.
     * @param id L'identifiant du participant à récupérer.
     * @return Le participant correspondant à l'identifiant.
     */
    public Participant getById(Long id) {
        return repository.getById(id);
    }

    /**
     * Récupère tous les participants enregistrés dans la base de données.
     * @return Une liste de tous les participants.
     */
    public List<Participant> getAll() {
        return repository.findAll();
    }

    /**
     * Crée un nouveau participant dans la base de données.
     * @param participant L'entité Participant à sauvegarder.
     * @return Le participant nouvellement créé et sauvegardé.
     */
    public Participant create(Participant participant) {
        return repository.save(participant);
    }

    /**
     * Met à jour les informations d'un participant existant.
     * @param id L'identifiant du participant à mettre à jour.
     * @param participant Les nouvelles informations du participant.
     * @return Le participant mis à jour, ou null si l'identifiant n'existe pas.
     */
    public Participant update(Long id, Participant participant) {
        // Vérifie si le participant existe avant de procéder à la mise à jour
        if (repository.findById(id).isPresent()) {
            // Définit l'identifiant pour mettre à jour l'entité existante
            participant.setParticipantId(id);
            return repository.save(participant);
        }
        // Retourne null si le participant n'existe pas
        return null;
    }

    /**
     * Supprime un participant de la base de données.
     * @param id L'identifiant du participant à supprimer.
     * @return 1 si la suppression est réussie, 0 si le participant n'existe pas.
     */
    public int delete(Long id) {
        // Vérifie si le participant existe avant de le supprimer
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return 1;
        }
        // Retourne 0 si le participant n'existe pas
        return 0;
    }
}
