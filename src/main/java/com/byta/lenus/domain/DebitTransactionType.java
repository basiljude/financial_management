package com.byta.lenus.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DebitTransactionType.
 */
@Entity
@Table(name = "debit_transaction_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DebitTransactionType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transactions")
    private String transactions;

    @ManyToOne
    private AddDebit addDebit;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactions() {
        return transactions;
    }

    public DebitTransactionType transactions(String transactions) {
        this.transactions = transactions;
        return this;
    }

    public void setTransactions(String transactions) {
        this.transactions = transactions;
    }

    public AddDebit getAddDebit() {
        return addDebit;
    }

    public DebitTransactionType addDebit(AddDebit addDebit) {
        this.addDebit = addDebit;
        return this;
    }

    public void setAddDebit(AddDebit addDebit) {
        this.addDebit = addDebit;
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
        DebitTransactionType debitTransactionType = (DebitTransactionType) o;
        if (debitTransactionType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), debitTransactionType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DebitTransactionType{" +
            "id=" + getId() +
            ", transactions='" + getTransactions() + "'" +
            "}";
    }
}
