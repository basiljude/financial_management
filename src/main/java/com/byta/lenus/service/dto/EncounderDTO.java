package com.byta.lenus.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Encounder entity.
 */
public class EncounderDTO implements Serializable {

    private Long id;

    private LocalDate outSet;

    private LocalDate end;

    private String department;

    private String vistType;

    private Long patientPaimentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOutSet() {
        return outSet;
    }

    public void setOutSet(LocalDate outSet) {
        this.outSet = outSet;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getVistType() {
        return vistType;
    }

    public void setVistType(String vistType) {
        this.vistType = vistType;
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

        EncounderDTO encounderDTO = (EncounderDTO) o;
        if(encounderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), encounderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EncounderDTO{" +
            "id=" + getId() +
            ", outSet='" + getOutSet() + "'" +
            ", end='" + getEnd() + "'" +
            ", department='" + getDepartment() + "'" +
            ", vistType='" + getVistType() + "'" +
            "}";
    }
}
