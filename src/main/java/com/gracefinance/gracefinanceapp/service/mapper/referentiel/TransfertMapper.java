package com.gracefinance.gracefinanceapp.service.mapper.referentiel;

import com.gracefinance.gracefinanceapp.domain.principal.Caisse;
import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import com.gracefinance.gracefinanceapp.domain.referentiel.Transfert;
import com.gracefinance.gracefinanceapp.service.dto.principal.CaisseDTO;
import com.gracefinance.gracefinanceapp.service.dto.principal.EntiteFinanciereDTO;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.TransfertDTO;
import com.gracefinance.gracefinanceapp.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transfert} and its DTO {@link TransfertDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransfertMapper extends EntityMapper<TransfertDTO, Transfert> {
    @Mapping(target = "entiteFinanciereSource", source = "entiteFinanciereSource", qualifiedByName = "entiteFinanciereId")
    @Mapping(target = "caisseSource", source = "caisseSource", qualifiedByName = "caisseId")
    @Mapping(target = "caisseDestination", source = "caisseDestination", qualifiedByName = "caisseId")
    TransfertDTO toDto(Transfert s);

    @Named("entiteFinanciereId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EntiteFinanciereDTO toDtoEntiteFinanciereId(EntiteFinanciere entiteFinanciere);

    @Named("caisseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CaisseDTO toDtoCaisseId(Caisse caisse);
}
