package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Commentaire;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.CommentaireRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des commentaires associés aux sondages.
 * Fournit des méthodes pour récupérer, ajouter, modifier et supprimer des commentaires.
 */
@Service
public class CommentaireService {

    // Déclaration du repository pour accéder à la base de données
    private final CommentaireRepository repository;
    // Services pour gérer les sondages et les participants
    private final SondageService sondageService;
    private final ParticipantService participantService;

    /**
     * Constructeur avec injection des dépendances.
     * @param repository Le repository pour les commentaires.
     * @param s Le service pour la gestion des sondages.
     * @param p Le service pour la gestion des participants.
     */
    public CommentaireService(CommentaireRepository repository, SondageService s, ParticipantService p) {
        this.repository = repository;
        this.sondageService = s;
        this.participantService = p;
    }

    /**
     * Récupère tous les commentaires liés à un sondage donné.
     * @param sondageId L'identifiant du sondage.
     * @return Une liste de commentaires associés au sondage.
     */
    public List<Commentaire> getBySondageId(Long sondageId) {
        return repository.getAllBySondage(sondageId);
    }

    /**
     * Ajoute un nouveau commentaire pour un sondage et un participant donnés.
     * @param idSondage L'identifiant du sondage associé.
     * @param idParticipant L'identifiant du participant associé.
     * @param commentaire Le commentaire à ajouter.
     * @return Le commentaire sauvegardé.
     */
    public Commentaire addCommantaire(Long idSondage, Long idParticipant, Commentaire commentaire) {
        // Associer le sondage au commentaire
        commentaire.setSondage(sondageService.getById(idSondage));
        // Associer le participant au commentaire
        commentaire.setParticipant(participantService.getById(idParticipant));
        // Sauvegarder le commentaire en base de données
        return repository.save(commentaire);
    }

    /**
     * Met à jour un commentaire existant.
     * @param id L'identifiant du commentaire à mettre à jour.
     * @param commentaire Les nouvelles informations du commentaire.
     * @return Le commentaire mis à jour, ou null si l'identifiant n'existe pas.
     */
    public Commentaire update(Long id, Commentaire commentaire) {
        // Vérifie si le commentaire existe dans la base de données
        if (repository.findById(id).isPresent()) {
            // Associe l'identifiant existant au commentaire mis à jour
            commentaire.setCommentaireId(id);
            return repository.save(commentaire);
        }
        // Retourne null si le commentaire n'existe pas
        return null;
    }

    /**
     * Supprime un commentaire existant.
     * @param id L'identifiant du commentaire à supprimer.
     * @return 1 si la suppression a réussi, 0 sinon.
     */
    public int delete(Long id) {
        // Vérifie si le commentaire existe dans la base de données
        if (repository.findById(id).isPresent()) {
            // Supprime le commentaire
            repository.deleteById(id);
            return 1;
        }
        // Retourne 0 si le commentaire n'existe pas
        return 0;
    }
}
