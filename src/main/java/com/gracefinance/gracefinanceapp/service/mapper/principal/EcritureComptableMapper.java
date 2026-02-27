package com.gracefinance.gracefinanceapp.service.mapper.principal;

import com.gracefinance.gracefinanceapp.domain.principal.EcritureComptable;
import com.gracefinance.gracefinanceapp.service.dto.principal.EcritureComptableDTO;
import com.gracefinance.gracefinanceapp.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EcritureComptable} and its DTO {@link EcritureComptableDTO}.
 */
@Mapper(componentModel = "spring")
public interface EcritureComptableMapper extends EntityMapper<EcritureComptableDTO, EcritureComptable> {}
