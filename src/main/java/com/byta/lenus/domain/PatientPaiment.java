package com.byta.lenus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PatientPaiment.
 */
@Entity
@Table(name = "patient_paiment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PatientPaiment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "amound")
    private Double amound;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private CashDesk encounder;

    @OneToMany(mappedBy = "patientPaiment")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Encounder> encounders = new HashSet<>();

    @OneToMany(mappedBy = "patientPaiment")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Type> types = new HashSet<>();

    @OneToMany(mappedBy = "patientPaiment")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Invoice> invoices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public PatientPaiment date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getAmound() {
        return amound;
    }

    public PatientPaiment amound(Double amound) {
        this.amound = amound;
        return this;
    }

    public void setAmound(Double amound) {
        this.amound = amound;
    }

    public String getDescription() {
        return description;
    }

    public PatientPaiment description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CashDesk getEncounder() {
        return encounder;
    }

    public PatientPaiment encounder(CashDesk cashDesk) {
        this.encounder = cashDesk;
        return this;
    }

    public void setEncounder(CashDesk cashDesk) {
        this.encounder = cashDesk;
    }

    public Set<Encounder> getEncounders() {
        return encounders;
    }

    public PatientPaiment encounders(Set<Encounder> encounders) {
        this.encounders = encounders;
        return this;
    }

    public PatientPaiment addEncounder(Encounder encounder) {
        this.encounders.add(encounder);
        encounder.setPatientPaiment(this);
        return this;
    }

    public PatientPaiment removeEncounder(Encounder encounder) {
        this.encounders.remove(encounder);
        encounder.setPatientPaiment(null);
        return this;
    }

    public void setEncounders(Set<Encounder> encounders) {
        this.encounders = encounders;
    }

    public Set<Type> getTypes() {
        return types;
    }

    public PatientPaiment types(Set<Type> types) {
        this.types = types;
        return this;
    }

    public PatientPaiment addType(Type type) {
        this.types.add(type);
        type.setPatientPaiment(this);
        return this;
    }

    public PatientPaiment removeType(Type type) {
        this.types.remove(type);
        type.setPatientPaiment(null);
        return this;
    }

    public void setTypes(Set<Type> types) {
        this.types = types;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public PatientPaiment invoices(Set<Invoice> invoices) {
        this.invoices = invoices;
        return this;
    }

    public PatientPaiment addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setPatientPaiment(this);
        return this;
    }

    public PatientPaiment removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setPatientPaiment(null);
        return this;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
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
        PatientPaiment patientPaiment = (PatientPaiment) o;
        if (patientPaiment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patientPaiment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PatientPaiment{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", amound='" + getAmound() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
