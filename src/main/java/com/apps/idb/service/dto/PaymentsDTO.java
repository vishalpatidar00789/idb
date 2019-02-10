package com.apps.idb.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.apps.idb.domain.enumeration.PaymentStatus;

/**
 * A DTO for the Payments entity.
 */
public class PaymentsDTO implements Serializable {

    private Long id;

    @NotNull
    private String vendor;

    @NotNull
    private PaymentStatus status;

    @NotNull
    private Integer paymentValue;

    private LocalDate initiatedDate;

    private LocalDate confirmDate;

    @NotNull
    private Boolean activated;

    @NotNull
    private LocalDate createdDate;

    @NotNull
    private String createdBy;

    private LocalDate lastUpdatedDate;

    private String lastUpdatedBy;


    private Long userAccountId;

    private Long appliedPackageId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Integer getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(Integer paymentValue) {
        this.paymentValue = paymentValue;
    }

    public LocalDate getInitiatedDate() {
        return initiatedDate;
    }

    public void setInitiatedDate(LocalDate initiatedDate) {
        this.initiatedDate = initiatedDate;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Long getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }

    public Long getAppliedPackageId() {
        return appliedPackageId;
    }

    public void setAppliedPackageId(Long packagesId) {
        this.appliedPackageId = packagesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaymentsDTO paymentsDTO = (PaymentsDTO) o;
        if (paymentsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentsDTO{" +
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
            ", userAccount=" + getUserAccountId() +
            ", appliedPackage=" + getAppliedPackageId() +
            "}";
    }
}
