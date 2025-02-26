package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de gestion des accès à la base de données pour l'entité {@link Sondage}.
 * Cette interface hérite de {@link JpaRepository}, permettant d'effectuer des opérations CRUD
 * (Create, Read, Update, Delete) sur les objets Sondage sans implémentation supplémentaire.
 */
@Repository
public interface SondageRepository extends JpaRepository<Sondage, Long> {
    // Les méthodes standard fournies par JpaRepository incluent :
    // - save(Sondage sondage) : pour sauvegarder ou mettre à jour un sondage.
    // - findById(Long id) : pour récupérer un sondage par son identifiant.
    // - findAll() : pour récupérer tous les sondages.
    // - delete(Sondage sondage) : pour supprimer un sondage.

    // Des méthodes personnalisées peuvent être ajoutées si nécessaire.
}
