package com.byta.lenus.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AddDebit entity.
 */
public class AddDebitDTO implements Serializable {

    private Long id;

    private LocalDate creditTime;

    private Double amound;

    private String comment;

    private Long debitCashDeskId;

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

    public Long getDebitCashDeskId() {
        return debitCashDeskId;
    }

    public void setDebitCashDeskId(Long debitCashDeskId) {
        this.debitCashDeskId = debitCashDeskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddDebitDTO addDebitDTO = (AddDebitDTO) o;
        if(addDebitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addDebitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddDebitDTO{" +
            "id=" + getId() +
            ", creditTime='" + getCreditTime() + "'" +
            ", amound='" + getAmound() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
