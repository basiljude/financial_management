package com.byta.lenus.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CashDesk entity.
 */
public class CashDeskDTO implements Serializable {

    private Long id;

    private String type;

    private Long addCreditId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getAddCreditId() {
        return addCreditId;
    }

    public void setAddCreditId(Long addCreditId) {
        this.addCreditId = addCreditId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CashDeskDTO cashDeskDTO = (CashDeskDTO) o;
        if(cashDeskDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cashDeskDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CashDeskDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
