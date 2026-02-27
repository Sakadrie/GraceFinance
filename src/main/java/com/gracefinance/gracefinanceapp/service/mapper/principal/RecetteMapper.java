package com.gracefinance.gracefinanceapp.service.mapper.principal;

import com.gracefinance.gracefinanceapp.domain.principal.Caisse;
import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import com.gracefinance.gracefinanceapp.domain.principal.Recette;
import com.gracefinance.gracefinanceapp.domain.referentiel.Categorie;
import com.gracefinance.gracefinanceapp.service.dto.principal.CaisseDTO;
import com.gracefinance.gracefinanceapp.service.dto.principal.EntiteFinanciereDTO;
import com.gracefinance.gracefinanceapp.service.dto.principal.RecetteDTO;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.CategorieDTO;
import com.gracefinance.gracefinanceapp.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Recette} and its DTO {@link RecetteDTO}.
 */
@Mapper(componentModel = "spring")
public interface RecetteMapper extends EntityMapper<RecetteDTO, Recette> {
    @Mapping(target = "entiteFinanciere", source = "entiteFinanciere", qualifiedByName = "entiteFinanciereId")
    @Mapping(target = "caisse", source = "caisse", qualifiedByName = "caisseId")
    @Mapping(target = "categorie", source = "categorie", qualifiedByName = "categorieId")
    RecetteDTO toDto(Recette s);

    @Named("entiteFinanciereId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EntiteFinanciereDTO toDtoEntiteFinanciereId(EntiteFinanciere entiteFinanciere);

    @Named("caisseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CaisseDTO toDtoCaisseId(Caisse caisse);

    @Named("categorieId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategorieDTO toDtoCategorieId(Categorie categorie);
}
