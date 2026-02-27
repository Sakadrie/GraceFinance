package com.gracefinance.gracefinanceapp.service.mapper.principal;

import com.gracefinance.gracefinanceapp.domain.principal.CompteComptable;
import com.gracefinance.gracefinanceapp.domain.principal.EcritureComptable;
import com.gracefinance.gracefinanceapp.domain.principal.LigneEcriture;
import com.gracefinance.gracefinanceapp.service.dto.principal.CompteComptableDTO;
import com.gracefinance.gracefinanceapp.service.dto.principal.EcritureComptableDTO;
import com.gracefinance.gracefinanceapp.service.dto.principal.LigneEcritureDTO;
import com.gracefinance.gracefinanceapp.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LigneEcriture} and its DTO {@link LigneEcritureDTO}.
 */
@Mapper(componentModel = "spring")
public interface LigneEcritureMapper extends EntityMapper<LigneEcritureDTO, LigneEcriture> {
    @Mapping(target = "ecriture", source = "ecriture", qualifiedByName = "ecritureComptableId")
    @Mapping(target = "compte", source = "compte", qualifiedByName = "compteComptableId")
    LigneEcritureDTO toDto(LigneEcriture s);

    @Named("ecritureComptableId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EcritureComptableDTO toDtoEcritureComptableId(EcritureComptable ecritureComptable);

    @Named("compteComptableId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompteComptableDTO toDtoCompteComptableId(CompteComptable compteComptable);
}
