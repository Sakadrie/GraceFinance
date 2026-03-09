package com.gracefinance.gracefinanceapp.service.mapper.referentiel;

import com.gracefinance.gracefinanceapp.domain.referentiel.District;
import com.gracefinance.gracefinanceapp.domain.referentiel.Ville;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.DistrictDTO;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.VilleDTO;
import com.gracefinance.gracefinanceapp.service.mapper.EntityMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = { DistrictMapper.class })
public interface VilleMapper extends EntityMapper<VilleDTO, Ville> {
    @Mapping(target = "district", source = "district", qualifiedByName = "districtId")
    VilleDTO toDto(Ville v);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    DistrictDTO toDtoDistrictId(District district);
}
