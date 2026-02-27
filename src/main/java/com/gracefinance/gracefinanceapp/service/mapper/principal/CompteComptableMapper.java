package com.gracefinance.gracefinanceapp.service.mapper.principal;

import com.gracefinance.gracefinanceapp.domain.principal.CompteComptable;
import com.gracefinance.gracefinanceapp.service.dto.principal.CompteComptableDTO;
import com.gracefinance.gracefinanceapp.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompteComptable} and its DTO {@link CompteComptableDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompteComptableMapper extends EntityMapper<CompteComptableDTO, CompteComptable> {}
