package com.byta.lenus.service.mapper;

import com.byta.lenus.domain.*;
import com.byta.lenus.service.dto.PatientPaimentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PatientPaiment and its DTO PatientPaimentDTO.
 */
@Mapper(componentModel = "spring", uses = {CashDeskMapper.class})
public interface PatientPaimentMapper extends EntityMapper<PatientPaimentDTO, PatientPaiment> {

    @Mapping(source = "encounder.id", target = "encounderId")
    PatientPaimentDTO toDto(PatientPaiment patientPaiment); 

    @Mapping(source = "encounderId", target = "encounder")
    @Mapping(target = "encounders", ignore = true)
    @Mapping(target = "types", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    PatientPaiment toEntity(PatientPaimentDTO patientPaimentDTO);

    default PatientPaiment fromId(Long id) {
        if (id == null) {
            return null;
        }
        PatientPaiment patientPaiment = new PatientPaiment();
        patientPaiment.setId(id);
        return patientPaiment;
    }
}
