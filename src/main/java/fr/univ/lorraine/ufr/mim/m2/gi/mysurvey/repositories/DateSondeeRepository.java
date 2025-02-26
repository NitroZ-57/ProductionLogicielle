package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Interface de gestion des accès à la base de données pour l'entité {@link DateSondee}.
 * Cette interface hérite de {@link JpaRepository}, fournissant des fonctionnalités CRUD par défaut
 * et permettant d'ajouter des requêtes personnalisées.
 */
@Repository
public interface DateSondeeRepository extends JpaRepository<DateSondee, Long> {

    /**
     * Méthode personnalisée pour trouver la meilleure date pour un sondage spécifique.
     * Cette requête native SQL calcule la date qui a reçu le plus de réponses "DISPONIBLE"
     * pour un sondage donné.
     *
     * @param id L'identifiant unique du sondage.
     * @return Une liste d'objets {@link Date} correspondant à la meilleure date pour le sondage.
     */
    @Query(value = "select d.date " +
            "FROM (SELECT max(nb) " +
            "FROM (SELECT date_sondage_id, count(choix) as nb " +
            "FROM date_sondee " +
            "WHERE choix = 'DISPONIBLE' " +
            "GROUP BY date_sondage_id) f) f1, " +
            "(SELECT date_sondage_id, count(choix) as nb " +
            "FROM date_sondee " +
            "WHERE choix = 'DISPONIBLE' " +
            "GROUP BY date_sondage_id) f2, date_sondage d " +
            "Where f2.nb = f1.max " +
            "and d.date_sondage_id = f2.date_sondage_id " +
            "and d.sondage_id = ?1",
            nativeQuery = true)
    List<Date> bestDate(Long id);

    /**
     * Méthode personnalisée pour trouver la meilleure date potentielle pour un sondage spécifique.
     * Cette requête native SQL calcule la date qui a reçu le plus de réponses "DISPONIBLE"
     * ou "PEUTETRE" pour un sondage donné.
     *
     * @param id L'identifiant unique du sondage.
     * @return Une liste d'objets {@link Date} correspondant à la meilleure date potentielle.
     */
    @Query(value = "select d.date " +
            "FROM (SELECT max(nb) " +
            "FROM (SELECT date_sondage_id, count(choix) as nb " +
            "FROM date_sondee " +
            "WHERE choix = 'DISPONIBLE' or choix = 'PEUTETRE' " +
            "GROUP BY date_sondage_id) f) f1, " +
            "(SELECT date_sondage_id, count(choix) as nb " +
            "FROM date_sondee " +
            "WHERE choix = 'DISPONIBLE' or choix = 'PEUTETRE' " +
            "GROUP BY date_sondage_id) f2, date_sondage d " +
            "Where f2.nb = f1.max " +
            "and d.date_sondage_id = f2.date_sondage_id " +
            "and d.sondage_id = ?1",
            nativeQuery = true)
    List<Date> maybeBestDate(Long id);
}
