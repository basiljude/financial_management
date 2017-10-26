package com.byta.lenus.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PatientPaiment entity.
 */
public class PatientPaimentDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private Double amound;

    private String description;

    private Long encounderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getAmound() {
        return amound;
    }

    public void setAmound(Double amound) {
        this.amound = amound;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEncounderId() {
        return encounderId;
    }

    public void setEncounderId(Long cashDeskId) {
        this.encounderId = cashDeskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PatientPaimentDTO patientPaimentDTO = (PatientPaimentDTO) o;
        if(patientPaimentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patientPaimentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PatientPaimentDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", amound='" + getAmound() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
