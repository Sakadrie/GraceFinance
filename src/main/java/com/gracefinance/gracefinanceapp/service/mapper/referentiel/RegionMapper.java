package com.gracefinance.gracefinanceapp.service.mapper.referentiel;

import com.gracefinance.gracefinanceapp.domain.referentiel.Region;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.RegionDTO;
import com.gracefinance.gracefinanceapp.service.mapper.EntityMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {}
