package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.controllers;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.ParticipantDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour gérer les participants d'un sondage.
 * Fournit des points de terminaison pour effectuer des opérations CRUD sur les participants.
 */
@RestController
@RequestMapping(value = "/api/participant") // Définit le point de base de l'API pour les participants
public class ParticipantController {

    private final ParticipantService service; // Service pour la logique métier des participants
    private final ModelMapper mapper; // Mapper pour convertir entre les entités et les DTO

    /**
     * Constructeur pour injecter les dépendances.
     *
     * @param service Service pour gérer les participants.
     * @param mapper Mapper pour la conversion entre DTO et entités.
     */
    public ParticipantController(ParticipantService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Méthode pour récupérer un participant par son identifiant.
     *
     * @param id Identifiant du participant à récupérer.
     * @return Un objet ParticipantDto représentant le participant.
     */
    @GetMapping(value = "/{id}") // Requête GET pour obtenir un participant par ID
    @ResponseStatus(HttpStatus.OK) // Retourne un statut 200 en cas de succès
    @ResponseBody
    public ParticipantDto get(@PathVariable("id") Long id) {
        // Recherche le participant par son ID
        var model = service.getById(id);
        // Convertit l'entité Participant en DTO
        return mapper.map(model, ParticipantDto.class);
    }

    /**
     * Méthode pour récupérer la liste de tous les participants.
     *
     * @return Une liste de ParticipantDto représentant tous les participants.
     */
    @GetMapping(value = "/") // Requête GET pour obtenir tous les participants
    @ResponseStatus(HttpStatus.OK) // Retourne un statut 200 en cas de succès
    @ResponseBody
    public List<ParticipantDto> get() {
        // Récupère tous les participants via le service
        var models = service.getAll();
        // Convertit la liste des entités Participant en liste de DTOs
        return models.stream().map(model -> mapper.map(model, ParticipantDto.class)).collect(Collectors.toList());
    }

    /**
     * Méthode pour créer un nouveau participant.
     *
     * @param participantDto Objet ParticipantDto contenant les données du participant à créer.
     * @return Un ParticipantDto représentant le participant créé.
     */
    @PostMapping(value = "/") // Requête POST pour créer un participant
    @ResponseStatus(HttpStatus.CREATED) // Retourne un statut 201 en cas de création réussie
    @ResponseBody
    public ParticipantDto create(@RequestBody ParticipantDto participantDto) {
        // Convertit le DTO en entité Participant
        var model = mapper.map(participantDto, Participant.class);
        // Appelle le service pour créer le participant
        var result = service.create(model);
        // Convertit le participant créé en DTO pour le retour
        return mapper.map(result, ParticipantDto.class);
    }

    /**
     * Méthode pour mettre à jour un participant existant.
     *
     * @param id Identifiant du participant à mettre à jour.
     * @param participantDto Objet ParticipantDto contenant les nouvelles données.
     * @return Un ParticipantDto représentant le participant mis à jour.
     */
    @PutMapping(value = "/{id}") // Requête PUT pour mettre à jour un participant par ID
    @ResponseStatus(HttpStatus.OK) // Retourne un statut 200 en cas de succès
    public ParticipantDto update(@PathVariable("id") Long id, @RequestBody ParticipantDto participantDto) {
        // Convertit le DTO en entité Participant
        var model = mapper.map(participantDto, Participant.class);
        // Appelle le service pour mettre à jour le participant
        var result = service.update(id, model);
        // Convertit le participant mis à jour en DTO pour le retour
        return mapper.map(result, ParticipantDto.class);
    }

    /**
     * Méthode pour supprimer un participant par son identifiant.
     *
     * @param id Identifiant du participant à supprimer.
     */
    @DeleteMapping(value = "/{id}") // Requête DELETE pour supprimer un participant par ID
    @ResponseStatus(HttpStatus.OK) // Retourne un statut 200 en cas de succès
    public void delete(@PathVariable("id") Long id) {
        // Appelle le service pour supprimer le participant
        service.delete(id);
    }
}
