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
 * A AddCredit.
 */
@Entity
@Table(name = "add_credit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AddCredit implements Serializable {

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
    private TransactionTypen transactionTypen;

    @OneToMany(mappedBy = "addCredit")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CashDesk> cashdesks = new HashSet<>();

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

    public AddCredit creditTime(LocalDate creditTime) {
        this.creditTime = creditTime;
        return this;
    }

    public void setCreditTime(LocalDate creditTime) {
        this.creditTime = creditTime;
    }

    public Double getAmound() {
        return amound;
    }

    public AddCredit amound(Double amound) {
        this.amound = amound;
        return this;
    }

    public void setAmound(Double amound) {
        this.amound = amound;
    }

    public String getComment() {
        return comment;
    }

    public AddCredit comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public TransactionTypen getTransactionTypen() {
        return transactionTypen;
    }

    public AddCredit transactionTypen(TransactionTypen transactionTypen) {
        this.transactionTypen = transactionTypen;
        return this;
    }

    public void setTransactionTypen(TransactionTypen transactionTypen) {
        this.transactionTypen = transactionTypen;
    }

    public Set<CashDesk> getCashdesks() {
        return cashdesks;
    }

    public AddCredit cashdesks(Set<CashDesk> cashDesks) {
        this.cashdesks = cashDesks;
        return this;
    }

    public AddCredit addCashdesk(CashDesk cashDesk) {
        this.cashdesks.add(cashDesk);
        cashDesk.setAddCredit(this);
        return this;
    }

    public AddCredit removeCashdesk(CashDesk cashDesk) {
        this.cashdesks.remove(cashDesk);
        cashDesk.setAddCredit(null);
        return this;
    }

    public void setCashdesks(Set<CashDesk> cashDesks) {
        this.cashdesks = cashDesks;
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
        AddCredit addCredit = (AddCredit) o;
        if (addCredit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addCredit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddCredit{" +
            "id=" + getId() +
            ", creditTime='" + getCreditTime() + "'" +
            ", amound='" + getAmound() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
