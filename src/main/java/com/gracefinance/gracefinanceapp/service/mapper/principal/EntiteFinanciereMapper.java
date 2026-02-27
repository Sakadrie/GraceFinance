package com.gracefinance.gracefinanceapp.service.mapper.principal;

import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import com.gracefinance.gracefinanceapp.service.dto.principal.EntiteFinanciereDTO;
import com.gracefinance.gracefinanceapp.service.mapper.EntityMapper;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EntiteFinanciere} and its DTO {@link EntiteFinanciereDTO}.
 */
@Mapper(componentModel = "spring")
public interface EntiteFinanciereMapper extends EntityMapper<EntiteFinanciereDTO, EntiteFinanciere> {
    @Mapping(target = "egliseLiees", source = "egliseLiees", qualifiedByName = "entiteFinanciereIdSet")
    @Mapping(target = "structureLiees", source = "structureLiees", qualifiedByName = "entiteFinanciereIdSet")
    EntiteFinanciereDTO toDto(EntiteFinanciere s);

    @Mapping(target = "removeEgliseLiee", ignore = true)
    @Mapping(target = "structureLiees", ignore = true)
    @Mapping(target = "removeStructureLiee", ignore = true)
    EntiteFinanciere toEntity(EntiteFinanciereDTO entiteFinanciereDTO);

    @Named("entiteFinanciereId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EntiteFinanciereDTO toDtoEntiteFinanciereId(EntiteFinanciere entiteFinanciere);

    @Named("entiteFinanciereIdSet")
    default Set<EntiteFinanciereDTO> toDtoEntiteFinanciereIdSet(Set<EntiteFinanciere> entiteFinanciere) {
        return entiteFinanciere.stream().map(this::toDtoEntiteFinanciereId).collect(Collectors.toSet());
    }
}
