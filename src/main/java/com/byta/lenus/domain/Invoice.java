package com.byta.lenus.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "invoice_number")
    private Integer invoiceNumber;

    @Column(name = "min_balance")
    private Double minBalance;

    @Column(name = "max_balance")
    private Double maxBalance;

    @Column(name = "invoice_status")
    private String invoiceStatus;

    @ManyToOne
    private PatientPaiment patientPaiment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public Invoice invoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public Invoice invoiceNumber(Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public void setInvoiceNumber(Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Double getMinBalance() {
        return minBalance;
    }

    public Invoice minBalance(Double minBalance) {
        this.minBalance = minBalance;
        return this;
    }

    public void setMinBalance(Double minBalance) {
        this.minBalance = minBalance;
    }

    public Double getMaxBalance() {
        return maxBalance;
    }

    public Invoice maxBalance(Double maxBalance) {
        this.maxBalance = maxBalance;
        return this;
    }

    public void setMaxBalance(Double maxBalance) {
        this.maxBalance = maxBalance;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public Invoice invoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
        return this;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public PatientPaiment getPatientPaiment() {
        return patientPaiment;
    }

    public Invoice patientPaiment(PatientPaiment patientPaiment) {
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
        Invoice invoice = (Invoice) o;
        if (invoice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", minBalance='" + getMinBalance() + "'" +
            ", maxBalance='" + getMaxBalance() + "'" +
            ", invoiceStatus='" + getInvoiceStatus() + "'" +
            "}";
    }
}
