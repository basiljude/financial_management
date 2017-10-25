package com.byta.lenus.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AddCredit entity.
 */
public class AddCreditDTO implements Serializable {

    private Long id;

    private LocalDate creditTime;

    private Double amound;

    private String comment;

    private Long transactionTypenId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreditTime() {
        return creditTime;
    }

    public void setCreditTime(LocalDate creditTime) {
        this.creditTime = creditTime;
    }

    public Double getAmound() {
        return amound;
    }

    public void setAmound(Double amound) {
        this.amound = amound;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getTransactionTypenId() {
        return transactionTypenId;
    }

    public void setTransactionTypenId(Long transactionTypenId) {
        this.transactionTypenId = transactionTypenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddCreditDTO addCreditDTO = (AddCreditDTO) o;
        if(addCreditDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addCreditDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddCreditDTO{" +
            "id=" + getId() +
            ", creditTime='" + getCreditTime() + "'" +
            ", amound='" + getAmound() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
