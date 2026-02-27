package com.gracefinance.gracefinanceapp.service.mapper.security;

import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import com.gracefinance.gracefinanceapp.domain.security.AffectationUtilisateur;
import com.gracefinance.gracefinanceapp.domain.security.Profil;
import com.gracefinance.gracefinanceapp.domain.security.User;
import com.gracefinance.gracefinanceapp.service.dto.principal.EntiteFinanciereDTO;
import com.gracefinance.gracefinanceapp.service.dto.security.AffectationUtilisateurDTO;
import com.gracefinance.gracefinanceapp.service.dto.security.ProfilDTO;
import com.gracefinance.gracefinanceapp.service.dto.security.UserDTO;
import com.gracefinance.gracefinanceapp.service.mapper.EntityMapper;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AffectationUtilisateur} and its DTO {@link AffectationUtilisateurDTO}.
 */
@Mapper(componentModel = "spring")
public interface AffectationUtilisateurMapper extends EntityMapper<AffectationUtilisateurDTO, AffectationUtilisateur> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "entiteFinanciere", source = "entiteFinanciere", qualifiedByName = "entiteFinanciereId")
    @Mapping(target = "profils", source = "profils", qualifiedByName = "profilIdSet")
    AffectationUtilisateurDTO toDto(AffectationUtilisateur s);

    @Mapping(target = "removeProfil", ignore = true)
    AffectationUtilisateur toEntity(AffectationUtilisateurDTO affectationUtilisateurDTO);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("entiteFinanciereId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EntiteFinanciereDTO toDtoEntiteFinanciereId(EntiteFinanciere entiteFinanciere);

    @Named("profilId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProfilDTO toDtoProfilId(Profil profil);

    @Named("profilIdSet")
    default Set<ProfilDTO> toDtoProfilIdSet(Set<Profil> profil) {
        return profil.stream().map(this::toDtoProfilId).collect(Collectors.toSet());
    }
}
