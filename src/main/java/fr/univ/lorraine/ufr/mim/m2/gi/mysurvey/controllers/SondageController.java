package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.controllers;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.CommentaireDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.DateSondageDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.SondageDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Commentaire;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.CommentaireService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.DateSondageService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.DateSondeeService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.SondageService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des sondages.
 * Fournit des points de terminaison pour effectuer des opérations CRUD sur les sondages,
 * ainsi que sur leurs dates associées et leurs commentaires.
 */
@RestController
@RequestMapping(value = "/api/sondage") // Point de base de l'API pour les sondages
public class SondageController {

    private final SondageService service; // Service pour la gestion des sondages
    private final CommentaireService scommentaire; // Service pour les commentaires
    private final DateSondageService sdate; // Service pour les dates des sondages
    private final DateSondeeService request; // Service pour les calculs sur les dates des sondages
    private final ModelMapper mapper; // Mapper pour convertir entre les entités et les DTO

    /**
     * Constructeur pour injecter les dépendances nécessaires.
     */
    public SondageController(SondageService service, ModelMapper mapper, CommentaireService c, DateSondageService d, DateSondeeService r) {
        this.service = service;
        this.mapper = mapper;
        this.sdate = d;
        this.scommentaire = c;
        this.request = r;
    }

    /**
     * Récupère un sondage par son identifiant.
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SondageDto get(@PathVariable("id") Long id) {
        var model = service.getById(id); // Récupération du sondage via le service
        return mapper.map(model, SondageDto.class); // Conversion de l'entité en DTO
    }

    /**
     * Récupère les meilleures dates pour un sondage donné.
     */
    @GetMapping(value = "/{id}/best")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Date> getBest(@PathVariable("id") Long id) {
        return request.bestDate(id); // Appel du service pour les meilleures dates
    }

    /**
     * Récupère les dates alternatives possibles pour un sondage donné.
     */
    @GetMapping(value = "/{id}/maybe")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Date> getMaybeBest(@PathVariable("id") Long id) {
        return request.maybeBestDate(id); // Appel du service pour des dates alternatives
    }

    /**
     * Récupère tous les sondages.
     */
    @GetMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<SondageDto> get() {
        var models = service.getAll(); // Récupération de tous les sondages via le service
        return models.stream()
                .map(model -> mapper.map(model, SondageDto.class)) // Conversion des entités en DTO
                .collect(Collectors.toList());
    }

    /**
     * Récupère tous les commentaires associés à un sondage donné.
     */
    @GetMapping(value = "/{id}/commentaires")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CommentaireDto> getCommentaires(@PathVariable("id") Long id) {
        var models = scommentaire.getBySondageId(id); // Récupération des commentaires
        return models.stream()
                .map(model -> mapper.map(model, CommentaireDto.class)) // Conversion en DTO
                .collect(Collectors.toList());
    }

    /**
     * Récupère toutes les dates associées à un sondage donné.
     */
    @GetMapping(value = "/{id}/dates")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<DateSondageDto> getDates(@PathVariable("id") Long id) {
        var models = sdate.getBySondageId(id); // Récupération des dates
        return models.stream()
                .map(model -> mapper.map(model, DateSondageDto.class)) // Conversion en DTO
                .collect(Collectors.toList());
    }

    /**
     * Crée un nouveau sondage.
     */
    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public SondageDto create(@RequestBody SondageDto sondageDto) {
        var model = mapper.map(sondageDto, Sondage.class); // Conversion du DTO en entité
        var result = service.create(sondageDto.getCreateBy(), model); // Création du sondage via le service
        return mapper.map(result, SondageDto.class); // Conversion de l'entité créée en DTO
    }

    /**
     * Ajoute un commentaire à un sondage existant.
     */
    @PostMapping(value = "/{id}/commentaires")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CommentaireDto createCommantaire(@PathVariable("id") Long id, @RequestBody CommentaireDto commantaireDto) {
        var model = mapper.map(commantaireDto, Commentaire.class); // Conversion du DTO en entité
        var result = scommentaire.addCommantaire(id, commantaireDto.getParticipant(), model); // Ajout du commentaire
        return mapper.map(result, CommentaireDto.class); // Conversion en DTO
    }

    /**
     * Ajoute une date à un sondage existant.
     */
    @PostMapping(value = "/{id}/dates")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public DateSondageDto createDate(@PathVariable("id") Long id, @RequestBody DateSondageDto dto) {
        var model = mapper.map(dto, DateSondage.class); // Conversion du DTO en entité
        var result = sdate.create(id, model); // Ajout de la date au sondage
        return mapper.map(result, DateSondageDto.class); // Conversion en DTO
    }

    /**
     * Met à jour un sondage existant.
     */
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SondageDto update(@PathVariable("id") Long id, @RequestBody SondageDto sondageDto) {
        var model = mapper.map(sondageDto, Sondage.class); // Conversion du DTO en entité
        var result = service.update(id, model); // Mise à jour du sondage
        return mapper.map(result, SondageDto.class); // Conversion en DTO
    }

    /**
     * Supprime un sondage existant.
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id); // Suppression du sondage via le service
    }
}
