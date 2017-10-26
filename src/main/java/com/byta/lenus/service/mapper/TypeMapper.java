package com.byta.lenus.service.mapper;

import com.byta.lenus.domain.*;
import com.byta.lenus.service.dto.TypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Type and its DTO TypeDTO.
 */
@Mapper(componentModel = "spring", uses = {PatientPaimentMapper.class})
public interface TypeMapper extends EntityMapper<TypeDTO, Type> {

    @Mapping(source = "patientPaiment.id", target = "patientPaimentId")
    TypeDTO toDto(Type type); 

    @Mapping(source = "patientPaimentId", target = "patientPaiment")
    Type toEntity(TypeDTO typeDTO);

    default Type fromId(Long id) {
        if (id == null) {
            return null;
        }
        Type type = new Type();
        type.setId(id);
        return type;
    }
}
