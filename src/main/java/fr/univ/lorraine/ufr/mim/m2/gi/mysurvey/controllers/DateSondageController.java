package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.controllers;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.DateSondeeDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondee;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.DateSondageService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.DateSondeeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour la gestion des sondages liés aux dates.
 * Permet la suppression de dates et la participation à des dates sondées.
 */
@RestController
@RequestMapping(value = "/api/date") // Point de base de l'API pour les ressources DateSondage
public class DateSondageController {

    private final DateSondageService service; // Service pour la gestion des sondages liés aux dates
    private final DateSondeeService sds; // Service pour la gestion des participations à une date sondée
    private final ModelMapper mapper; // Mapper pour convertir entre les DTOs et les entités

    /**
     * Constructeur avec injection des dépendances.
     *
     * @param service Service pour gérer les dates des sondages.
     * @param mapper Instance de ModelMapper pour les conversions.
     * @param s Service pour gérer les participations aux dates sondées.
     */
    public DateSondageController(DateSondageService service, ModelMapper mapper, DateSondeeService s) {
        this.service = service;
        this.mapper = mapper;
        this.sds = s;
    }

    /**
     * Méthode pour supprimer une date associée à un sondage.
     *
     * @param id Identifiant de la date à supprimer.
     */
    @DeleteMapping(value = "/{id}") // Requête DELETE pour supprimer une date par son identifiant
    @ResponseStatus(HttpStatus.OK) // Code HTTP 200 en cas de succès
    public void delete(@PathVariable("id") Long id) {
        // Suppression de la date via le service
        service.delete(id);
    }

    /**
     * Méthode pour créer une participation à une date sondée.
     *
     * @param id Identifiant de la date sondée à laquelle participer.
     * @param dto Objet DateSondeeDto contenant les informations de participation.
     * @return Le DTO de la participation créée.
     */
    @PostMapping(value = "/{id}/participer") // Requête POST pour participer à une date sondée
    @ResponseStatus(HttpStatus.CREATED) // Code HTTP 201 en cas de création réussie
    public DateSondeeDto createParticipation(@PathVariable("id") Long id, @RequestBody DateSondeeDto dto) {
        // Conversion du DTO en entité DateSondee
        var model = mapper.map(dto, DateSondee.class);

        // Appel du service pour créer la participation
        var result = sds.create(id, dto.getParticipant(), model);

        // Conversion du résultat en DTO et retour au client
        return mapper.map(result, DateSondeeDto.class);
    }
}
