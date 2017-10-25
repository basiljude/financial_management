package com.byta.lenus.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DebitCashDesk entity.
 */
public class DebitCashDeskDTO implements Serializable {

    private Long id;

    private String type;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DebitCashDeskDTO debitCashDeskDTO = (DebitCashDeskDTO) o;
        if(debitCashDeskDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), debitCashDeskDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DebitCashDeskDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
