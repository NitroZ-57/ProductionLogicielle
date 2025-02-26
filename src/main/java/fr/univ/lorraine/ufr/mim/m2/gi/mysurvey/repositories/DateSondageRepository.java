package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface de gestion des accès à la base de données pour l'entité {@link DateSondage}.
 * Cette interface hérite de {@link JpaRepository}, fournissant des fonctionnalités CRUD par défaut
 * et permettant d'ajouter des requêtes personnalisées.
 */
@Repository
public interface DateSondageRepository extends JpaRepository<DateSondage, Long> {

    /**
     * Méthode personnalisée pour récupérer toutes les dates associées à un sondage spécifique.
     *
     * @param id L'identifiant unique du sondage.
     * @return Une liste de dates associées au sondage ayant l'identifiant donné.
     */
    @Query("SELECT c FROM DateSondage c WHERE c.sondage.sondageId = :id")
    List<DateSondage> getAllBySondage(Long id);
}
