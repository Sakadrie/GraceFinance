package com.gracefinance.gracefinanceapp.service.mapper.referentiel;

import com.gracefinance.gracefinanceapp.domain.referentiel.Region;
import com.gracefinance.gracefinanceapp.domain.referentiel.SousRegion;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.RegionDTO;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.SousRegionDTO;
import com.gracefinance.gracefinanceapp.service.mapper.EntityMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = { RegionMapper.class })
public interface SousRegionMapper extends EntityMapper<SousRegionDTO, SousRegion> {
    @Mapping(target = "region", source = "region", qualifiedByName = "regionId")
    SousRegionDTO toDto(SousRegion s);

    @Named("regionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    RegionDTO toDtoRegionId(Region region);
}
