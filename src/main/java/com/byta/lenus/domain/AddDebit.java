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
 * A AddDebit.
 */
@Entity
@Table(name = "add_debit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AddDebit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "credit_time")
    private LocalDate creditTime;

    @Column(name = "amound")
    private Double amound;

    @Column(name = "jhi_comment")
    private String comment;

    @OneToOne
    @JoinColumn(unique = true)
    private DebitCashDesk debitCashDesk;

    @OneToMany(mappedBy = "addDebit")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DebitTransactionType> debitTransactionTypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreditTime() {
        return creditTime;
    }

    public AddDebit creditTime(LocalDate creditTime) {
        this.creditTime = creditTime;
        return this;
    }

    public void setCreditTime(LocalDate creditTime) {
        this.creditTime = creditTime;
    }

    public Double getAmound() {
        return amound;
    }

    public AddDebit amound(Double amound) {
        this.amound = amound;
        return this;
    }

    public void setAmound(Double amound) {
        this.amound = amound;
    }

    public String getComment() {
        return comment;
    }

    public AddDebit comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public DebitCashDesk getDebitCashDesk() {
        return debitCashDesk;
    }

    public AddDebit debitCashDesk(DebitCashDesk debitCashDesk) {
        this.debitCashDesk = debitCashDesk;
        return this;
    }

    public void setDebitCashDesk(DebitCashDesk debitCashDesk) {
        this.debitCashDesk = debitCashDesk;
    }

    public Set<DebitTransactionType> getDebitTransactionTypes() {
        return debitTransactionTypes;
    }

    public AddDebit debitTransactionTypes(Set<DebitTransactionType> debitTransactionTypes) {
        this.debitTransactionTypes = debitTransactionTypes;
        return this;
    }

    public AddDebit addDebitTransactionType(DebitTransactionType debitTransactionType) {
        this.debitTransactionTypes.add(debitTransactionType);
        debitTransactionType.setAddDebit(this);
        return this;
    }

    public AddDebit removeDebitTransactionType(DebitTransactionType debitTransactionType) {
        this.debitTransactionTypes.remove(debitTransactionType);
        debitTransactionType.setAddDebit(null);
        return this;
    }

    public void setDebitTransactionTypes(Set<DebitTransactionType> debitTransactionTypes) {
        this.debitTransactionTypes = debitTransactionTypes;
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
        AddDebit addDebit = (AddDebit) o;
        if (addDebit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addDebit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddDebit{" +
            "id=" + getId() +
            ", creditTime='" + getCreditTime() + "'" +
            ", amound='" + getAmound() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
