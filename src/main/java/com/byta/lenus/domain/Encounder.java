package com.byta.lenus.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Encounder.
 */
@Entity
@Table(name = "encounder")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Encounder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "out_set")
    private LocalDate outSet;

    @Column(name = "jhi_end")
    private LocalDate end;

    @Column(name = "department")
    private String department;

    @Column(name = "vist_type")
    private String vistType;

    @ManyToOne
    private PatientPaiment patientPaiment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOutSet() {
        return outSet;
    }

    public Encounder outSet(LocalDate outSet) {
        this.outSet = outSet;
        return this;
    }

    public void setOutSet(LocalDate outSet) {
        this.outSet = outSet;
    }

    public LocalDate getEnd() {
        return end;
    }

    public Encounder end(LocalDate end) {
        this.end = end;
        return this;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getDepartment() {
        return department;
    }

    public Encounder department(String department) {
        this.department = department;
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getVistType() {
        return vistType;
    }

    public Encounder vistType(String vistType) {
        this.vistType = vistType;
        return this;
    }

    public void setVistType(String vistType) {
        this.vistType = vistType;
    }

    public PatientPaiment getPatientPaiment() {
        return patientPaiment;
    }

    public Encounder patientPaiment(PatientPaiment patientPaiment) {
        this.patientPaiment = patientPaiment;
        return this;
    }

    public void setPatientPaiment(PatientPaiment patientPaiment) {
        this.patientPaiment = patientPaiment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Encounder encounder = (Encounder) o;
        if (encounder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), encounder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Encounder{" +
            "id=" + getId() +
            ", outSet='" + getOutSet() + "'" +
            ", end='" + getEnd() + "'" +
            ", department='" + getDepartment() + "'" +
            ", vistType='" + getVistType() + "'" +
            "}";
    }
}
