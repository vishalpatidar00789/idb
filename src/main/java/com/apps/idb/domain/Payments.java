package com.apps.idb.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.apps.idb.domain.enumeration.PaymentStatus;

/**
 * A Payments.
 */
@Entity
@Table(name = "payments")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Payments implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "vendor", nullable = false)
    private String vendor;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @NotNull
    @Column(name = "payment_value", nullable = false)
    private Integer paymentValue;

    @Column(name = "initiated_date")
    private LocalDate initiatedDate;

    @Column(name = "confirm_date")
    private LocalDate confirmDate;

    @NotNull
    @Column(name = "activated", nullable = false)
    private Boolean activated;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "last_updated_date")
    private LocalDate lastUpdatedDate;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @ManyToOne
    @JsonIgnoreProperties("payments")
    private UserAccount userAccount;

    @ManyToOne
    @JsonIgnoreProperties("packages")
    private Packages appliedPackage;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVendor() {
        return vendor;
    }

    public Payments vendor(String vendor) {
        this.vendor = vendor;
        return this;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Payments status(PaymentStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Integer getPaymentValue() {
        return paymentValue;
    }

    public Payments paymentValue(Integer paymentValue) {
        this.paymentValue = paymentValue;
        return this;
    }

    public void setPaymentValue(Integer paymentValue) {
        this.paymentValue = paymentValue;
    }

    public LocalDate getInitiatedDate() {
        return initiatedDate;
    }

    public Payments initiatedDate(LocalDate initiatedDate) {
        this.initiatedDate = initiatedDate;
        return this;
    }

    public void setInitiatedDate(LocalDate initiatedDate) {
        this.initiatedDate = initiatedDate;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public Payments confirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
        return this;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Boolean isActivated() {
        return activated;
    }

    public Payments activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Payments createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Payments createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Payments lastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public Payments lastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public Payments userAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Packages getAppliedPackage() {
        return appliedPackage;
    }

    public Payments appliedPackage(Packages packages) {
        this.appliedPackage = packages;
        return this;
    }

    public void setAppliedPackage(Packages packages) {
        this.appliedPackage = packages;
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
        Payments payments = (Payments) o;
        if (payments.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), payments.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Payments{" +
            "id=" + getId() +
            ", vendor='" + getVendor() + "'" +
            ", status='" + getStatus() + "'" +
            ", paymentValue=" + getPaymentValue() +
            ", initiatedDate='" + getInitiatedDate() + "'" +
            ", confirmDate='" + getConfirmDate() + "'" +
            ", activated='" + isActivated() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            "}";
    }
}
