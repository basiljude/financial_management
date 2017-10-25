package com.byta.lenus.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TransactionTypen entity.
 */
public class TransactionTypenDTO implements Serializable {

    private Long id;

    private String transactions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactions() {
        return transactions;
    }

    public void setTransactions(String transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransactionTypenDTO transactionTypenDTO = (TransactionTypenDTO) o;
        if(transactionTypenDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionTypenDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionTypenDTO{" +
            "id=" + getId() +
            ", transactions='" + getTransactions() + "'" +
            "}";
    }
}
