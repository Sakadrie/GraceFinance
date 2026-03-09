package com.gracefinance.gracefinanceapp.service.mapper.referentiel;

import com.gracefinance.gracefinanceapp.domain.referentiel.District;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.DistrictDTO;
import com.gracefinance.gracefinanceapp.service.mapper.EntityMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { SousRegionMapper.class })
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {
    @Mapping(source = "sousRegion.id", target = "sousRegionId")
    @Mapping(source = "sousRegion.nom", target = "sousRegionNom")
    DistrictDTO toDto(District d);

    @Mapping(source = "sousRegionId", target = "sousRegion")
    District toEntity(DistrictDTO dto);
}
