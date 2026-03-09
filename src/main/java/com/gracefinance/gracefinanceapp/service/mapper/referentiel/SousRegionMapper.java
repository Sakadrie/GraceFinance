package com.gracefinance.gracefinanceapp.service.mapper.referentiel;

import com.gracefinance.gracefinanceapp.domain.referentiel.SousRegion;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.SousRegionDTO;
import com.gracefinance.gracefinanceapp.service.mapper.EntityMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { RegionMapper.class })
public interface SousRegionMapper extends EntityMapper<SousRegionDTO, SousRegion> {
    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "region.nom", target = "regionNom")
    SousRegionDTO toDto(SousRegion s);

    @Mapping(source = "regionId", target = "region")
    SousRegion toEntity(SousRegionDTO dto);
}
