package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.configurations;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.CommentaireDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.DateSondeeDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.ParticipantDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.SondageDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Commentaire;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondee;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuration pour définir et personnaliser le bean ModelMapper.
 * ModelMapper est utilisé pour effectuer le mapping entre les DTOs (Data Transfer Objects) et les entités.
 */
@Configuration
public class ModelMapperConfig {

    /**
     * Configure et retourne une instance de ModelMapper avec des règles de mapping personnalisées.
     *
     * @return une instance configurée de ModelMapper.
     */
    @Bean
    public ModelMapper modelMapper() {

        // Création d'une instance de ModelMapper
        var mapper = new ModelMapper();

        // Définir une stratégie de correspondance STRICT pour un mapping précis
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Règles de mapping personnalisées entre CommentaireDto et Commentaire
        mapper.createTypeMap(CommentaireDto.class, Commentaire.class).addMappings(
                // Ignorer le champ 'participant' lors de la conversion vers Commentaire
                m -> m.skip(Commentaire::setParticipant)
        );
        mapper.createTypeMap(Commentaire.class, CommentaireDto.class).addMappings(
                // Mapper le 'participantId' de l'objet Participant vers CommentaireDto
                m -> m.map(src -> src.getParticipant().getParticipantId(), CommentaireDto::setParticipant)
        );

        // Règles de mapping personnalisées entre DateSondeeDto et DateSondee
        mapper.createTypeMap(DateSondeeDto.class, DateSondee.class).addMappings(
                // Ignorer le champ 'participant' lors de la conversion vers DateSondee
                m -> m.skip(DateSondee::setParticipant)
        );
        mapper.createTypeMap(DateSondee.class, DateSondeeDto.class).addMappings(
                // Mapper le 'participantId' de l'objet Participant vers DateSondeeDto
                m -> m.map(src -> src.getParticipant().getParticipantId(), DateSondeeDto::setParticipant)
        );

        // Règles de mapping personnalisées entre Participant et ParticipantDto
        mapper.createTypeMap(Participant.class, ParticipantDto.class).addMappings(
                // Mapper directement le champ 'participantId'
                m -> m.map(Participant::getParticipantId, ParticipantDto::setParticipantId)
        );

        // Règles de mapping personnalisées entre SondageDto et Sondage
        mapper.createTypeMap(SondageDto.class, Sondage.class).addMappings(
                // Ignorer le champ 'createBy' lors de la conversion vers Sondage
                m -> m.skip(Sondage::setCreateBy)
        );
        mapper.createTypeMap(Sondage.class, SondageDto.class).addMappings(
                // Mapper le 'participantId' de l'objet 'createBy' vers SondageDto
                m -> m.map(src -> src.getCreateBy().getParticipantId(), SondageDto::setCreateBy)
        );

        // Règles de mapping pour le champ 'choix' entre DateSondeeDto et DateSondee
        mapper.typeMap(DateSondeeDto.class, DateSondee.class).addMappings(
                m -> m.map(DateSondeeDto::getChoix, DateSondee::setChoix)
        );
        mapper.typeMap(DateSondee.class, DateSondeeDto.class).addMappings(
                m -> m.map(DateSondee::getChoix, DateSondeeDto::setChoix)
        );

        // Mapping supplémentaire pour ParticipantDto et Participant
        mapper.typeMap(ParticipantDto.class, Participant.class).addMappings(
                m -> m.map(ParticipantDto::getParticipantId, Participant::setParticipantId)
        );
        mapper.typeMap(Participant.class, ParticipantDto.class).addMappings(
                m -> m.map(Participant::getParticipantId, ParticipantDto::setParticipantId)
        );

        // Retourner l'instance configurée de ModelMapper
        return mapper;
    }
}
