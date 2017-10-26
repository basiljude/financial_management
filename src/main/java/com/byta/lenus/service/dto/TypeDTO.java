package com.byta.lenus.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Type entity.
 */
public class TypeDTO implements Serializable {

    private Long id;

    private String type;

    private Long patientPaimentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPatientPaimentId() {
        return patientPaimentId;
    }

    public void setPatientPaimentId(Long patientPaimentId) {
        this.patientPaimentId = patientPaimentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypeDTO typeDTO = (TypeDTO) o;
        if(typeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
