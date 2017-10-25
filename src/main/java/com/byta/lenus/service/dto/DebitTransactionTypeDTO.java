package com.byta.lenus.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the DebitTransactionType entity.
 */
public class DebitTransactionTypeDTO implements Serializable {

    private Long id;

    private String transactions;

    private Long addDebitId;

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

    public Long getAddDebitId() {
        return addDebitId;
    }

    public void setAddDebitId(Long addDebitId) {
        this.addDebitId = addDebitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DebitTransactionTypeDTO debitTransactionTypeDTO = (DebitTransactionTypeDTO) o;
        if(debitTransactionTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), debitTransactionTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DebitTransactionTypeDTO{" +
            "id=" + getId() +
            ", transactions='" + getTransactions() + "'" +
            "}";
    }
}
