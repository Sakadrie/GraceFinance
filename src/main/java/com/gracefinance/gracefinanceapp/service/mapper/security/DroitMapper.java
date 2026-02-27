package com.gracefinance.gracefinanceapp.service.mapper.security;

import com.gracefinance.gracefinanceapp.domain.security.Droit;
import com.gracefinance.gracefinanceapp.domain.security.Profil;
import com.gracefinance.gracefinanceapp.service.dto.security.DroitDTO;
import com.gracefinance.gracefinanceapp.service.dto.security.ProfilDTO;
import com.gracefinance.gracefinanceapp.service.mapper.EntityMapper;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Droit} and its DTO {@link DroitDTO}.
 */
@Mapper(componentModel = "spring")
public interface DroitMapper extends EntityMapper<DroitDTO, Droit> {
    @Mapping(target = "profils", source = "profils", qualifiedByName = "profilIdSet")
    DroitDTO toDto(Droit s);

    @Mapping(target = "profils", ignore = true)
    @Mapping(target = "removeProfil", ignore = true)
    Droit toEntity(DroitDTO droitDTO);

    @Named("profilId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProfilDTO toDtoProfilId(Profil profil);

    @Named("profilIdSet")
    default Set<ProfilDTO> toDtoProfilIdSet(Set<Profil> profil) {
        return profil.stream().map(this::toDtoProfilId).collect(Collectors.toSet());
    }
}
