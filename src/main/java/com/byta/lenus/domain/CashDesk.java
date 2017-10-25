package com.byta.lenus.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CashDesk.
 */
@Entity
@Table(name = "cash_desk")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CashDesk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_type")
    private String type;

    @ManyToOne
    private AddCredit addCredit;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public CashDesk type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AddCredit getAddCredit() {
        return addCredit;
    }

    public CashDesk addCredit(AddCredit addCredit) {
        this.addCredit = addCredit;
        return this;
    }

    public void setAddCredit(AddCredit addCredit) {
        this.addCredit = addCredit;
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
        CashDesk cashDesk = (CashDesk) o;
        if (cashDesk.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cashDesk.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CashDesk{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
