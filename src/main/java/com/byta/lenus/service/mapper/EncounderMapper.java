package com.byta.lenus.service.mapper;

import com.byta.lenus.domain.*;
import com.byta.lenus.service.dto.EncounderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Encounder and its DTO EncounderDTO.
 */
@Mapper(componentModel = "spring", uses = {PatientPaimentMapper.class})
public interface EncounderMapper extends EntityMapper<EncounderDTO, Encounder> {

    @Mapping(source = "patientPaiment.id", target = "patientPaimentId")
    EncounderDTO toDto(Encounder encounder); 

    @Mapping(source = "patientPaimentId", target = "patientPaiment")
    Encounder toEntity(EncounderDTO encounderDTO);

    default Encounder fromId(Long id) {
        if (id == null) {
            return null;
        }
        Encounder encounder = new Encounder();
        encounder.setId(id);
        return encounder;
    }
}
