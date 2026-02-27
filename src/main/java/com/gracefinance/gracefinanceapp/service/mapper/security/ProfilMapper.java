package com.gracefinance.gracefinanceapp.service.mapper.security;

import com.gracefinance.gracefinanceapp.domain.security.AffectationUtilisateur;
import com.gracefinance.gracefinanceapp.domain.security.Droit;
import com.gracefinance.gracefinanceapp.domain.security.Profil;
import com.gracefinance.gracefinanceapp.service.dto.security.AffectationUtilisateurDTO;
import com.gracefinance.gracefinanceapp.service.dto.security.DroitDTO;
import com.gracefinance.gracefinanceapp.service.dto.security.ProfilDTO;
import com.gracefinance.gracefinanceapp.service.mapper.EntityMapper;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Profil} and its DTO {@link ProfilDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProfilMapper extends EntityMapper<ProfilDTO, Profil> {
    @Mapping(target = "droits", source = "droits", qualifiedByName = "droitIdSet")
    @Mapping(target = "affectations", source = "affectations", qualifiedByName = "affectationUtilisateurIdSet")
    ProfilDTO toDto(Profil s);

    @Mapping(target = "removeDroit", ignore = true)
    @Mapping(target = "affectations", ignore = true)
    @Mapping(target = "removeAffectation", ignore = true)
    Profil toEntity(ProfilDTO profilDTO);

    @Named("droitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DroitDTO toDtoDroitId(Droit droit);

    @Named("droitIdSet")
    default Set<DroitDTO> toDtoDroitIdSet(Set<Droit> droit) {
        return droit.stream().map(this::toDtoDroitId).collect(Collectors.toSet());
    }

    @Named("affectationUtilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AffectationUtilisateurDTO toDtoAffectationUtilisateurId(AffectationUtilisateur affectationUtilisateur);

    @Named("affectationUtilisateurIdSet")
    default Set<AffectationUtilisateurDTO> toDtoAffectationUtilisateurIdSet(Set<AffectationUtilisateur> affectationUtilisateur) {
        return affectationUtilisateur.stream().map(this::toDtoAffectationUtilisateurId).collect(Collectors.toSet());
    }
}
