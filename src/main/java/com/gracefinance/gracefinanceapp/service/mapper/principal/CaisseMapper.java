package com.gracefinance.gracefinanceapp.service.mapper.principal;

import com.gracefinance.gracefinanceapp.domain.principal.Caisse;
import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import com.gracefinance.gracefinanceapp.service.dto.principal.CaisseDTO;
import com.gracefinance.gracefinanceapp.service.dto.principal.EntiteFinanciereDTO;
import com.gracefinance.gracefinanceapp.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Caisse} and its DTO {@link CaisseDTO}.
 */
@Mapper(componentModel = "spring")
public interface CaisseMapper extends EntityMapper<CaisseDTO, Caisse> {
    @Mapping(target = "entiteFinanciere", source = "entiteFinanciere", qualifiedByName = "entiteFinanciereId")
    CaisseDTO toDto(Caisse s);

    @Named("entiteFinanciereId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EntiteFinanciereDTO toDtoEntiteFinanciereId(EntiteFinanciere entiteFinanciere);
}
