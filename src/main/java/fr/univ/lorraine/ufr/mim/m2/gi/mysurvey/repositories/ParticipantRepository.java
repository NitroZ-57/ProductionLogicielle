package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de gestion des accès à la base de données pour l'entité {@link Participant}.
 * Cette interface hérite de {@link JpaRepository}, ce qui permet d'effectuer des opérations CRUD
 * (Create, Read, Update, Delete) sur les objets Participant sans implémentation supplémentaire.
 */
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    // Cette interface utilise les méthodes de JpaRepository pour gérer les Participants.
    // Aucune méthode personnalisée n'est définie ici, mais elles peuvent être ajoutées si nécessaire.
}
