package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.controllers;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.CommentaireDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Commentaire;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.CommentaireService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour la gestion des commentaires.
 * Gère les opérations de mise à jour et de suppression des entités Commentaire.
 */
@RestController
@RequestMapping(value = "/api/commentaire") // Point d'entrée API pour les ressources Commentaire
public class CommentaireController {

    private final CommentaireService service; // Service pour gérer la logique métier des commentaires
    private final ModelMapper mapper; // Mapper pour convertir entre DTOs et entités

    /**
     * Constructeur pour injecter les dépendances du service et du mapper.
     *
     * @param service Service de gestion des commentaires.
     * @param mapper Instance de ModelMapper pour les conversions.
     */
    public CommentaireController(CommentaireService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Méthode pour mettre à jour un commentaire existant.
     *
     * @param id Identifiant du commentaire à mettre à jour.
     * @param commentaireDto Données du commentaire mises à jour.
     * @return Le DTO du commentaire mis à jour.
     */
    @PutMapping(value = "/{id}") // Requête PUT pour mettre à jour un commentaire
    @ResponseStatus(HttpStatus.OK) // Code HTTP 200 en cas de succès
    @ResponseBody
    public CommentaireDto update(@PathVariable("id") Long id, @RequestBody CommentaireDto commentaireDto) {
        // Conversion du DTO en entité Commentaire
        var model = mapper.map(commentaireDto, Commentaire.class);

        // Mise à jour du commentaire via le service
        var result = service.update(id, model);

        // Conversion de l'entité mise à jour en DTO et retour au client
        return mapper.map(result, CommentaireDto.class);
    }

    /**
     * Méthode pour supprimer un commentaire existant.
     *
     * @param id Identifiant du commentaire à supprimer.
     */
    @DeleteMapping(value = "/{id}") // Requête DELETE pour supprimer un commentaire
    @ResponseStatus(HttpStatus.OK) // Code HTTP 200 en cas de succès
    public void delete(@PathVariable("id") Long id) {
        // Suppression du commentaire via le service
        service.delete(id);
    }
}
