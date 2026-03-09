package com.gracefinance.gracefinanceapp.service.mapper.referentiel;

import com.gracefinance.gracefinanceapp.domain.referentiel.District;
import com.gracefinance.gracefinanceapp.domain.referentiel.SousRegion;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.DistrictDTO;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.SousRegionDTO;
import com.gracefinance.gracefinanceapp.service.mapper.EntityMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { SousRegionMapper.class })
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {
    @Mapping(target = "sousRegion", source = "sousRegion", qualifiedByName = "sousRegionId")
    DistrictDTO toDto(District d);

    @Named("sousRegionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    SousRegionDTO toDtoSousRegionId(SousRegion sousRegion);
}
