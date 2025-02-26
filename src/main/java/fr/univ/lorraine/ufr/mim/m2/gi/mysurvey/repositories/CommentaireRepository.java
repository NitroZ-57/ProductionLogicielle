package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface de gestion des accès à la base de données pour l'entité {@link Commentaire}.
 * Cette interface hérite de {@link JpaRepository}, fournissant des méthodes CRUD par défaut
 * et permettant la définition de requêtes personnalisées.
 */
@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

    /**
     * Méthode personnalisée pour récupérer tous les commentaires associés à un sondage spécifique.
     *
     * @param id L'identifiant unique du sondage.
     * @return Une liste de commentaires associés au sondage ayant l'identifiant donné.
     */
    @Query("SELECT c FROM Commentaire c WHERE c.sondage.sondageId = :id")
    List<Commentaire> getAllBySondage(Long id);

}
