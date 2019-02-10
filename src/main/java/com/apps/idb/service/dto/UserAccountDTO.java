package com.apps.idb.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.apps.idb.domain.enumeration.AccountType;

/**
 * A DTO for the UserAccount entity.
 */
public class UserAccountDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer remainingChapters;

    @NotNull
    private LocalDate currPackageStartDate;

    @NotNull
    private LocalDate currPackageEndDate;

    private Integer userDiscount;

    @NotNull
    private String activated;

    @NotNull
    private AccountType accountType;

    @NotNull
    private Integer perDayChapterLimit;

    @NotNull
    private LocalDate createdDate;

    @NotNull
    private String createdBy;

    private LocalDate lastUpdatedDate;

    private String lastUpdatedBy;


    private Long userId;

    private String userEmail;

    private Long currentPackageId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRemainingChapters() {
        return remainingChapters;
    }

    public void setRemainingChapters(Integer remainingChapters) {
        this.remainingChapters = remainingChapters;
    }

    public LocalDate getCurrPackageStartDate() {
        return currPackageStartDate;
    }

    public void setCurrPackageStartDate(LocalDate currPackageStartDate) {
        this.currPackageStartDate = currPackageStartDate;
    }

    public LocalDate getCurrPackageEndDate() {
        return currPackageEndDate;
    }

    public void setCurrPackageEndDate(LocalDate currPackageEndDate) {
        this.currPackageEndDate = currPackageEndDate;
    }

    public Integer getUserDiscount() {
        return userDiscount;
    }

    public void setUserDiscount(Integer userDiscount) {
        this.userDiscount = userDiscount;
    }

    public String getActivated() {
        return activated;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Integer getPerDayChapterLimit() {
        return perDayChapterLimit;
    }

    public void setPerDayChapterLimit(Integer perDayChapterLimit) {
        this.perDayChapterLimit = perDayChapterLimit;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long iDBUserId) {
        this.userId = iDBUserId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String iDBUserEmail) {
        this.userEmail = iDBUserEmail;
    }

    public Long getCurrentPackageId() {
        return currentPackageId;
    }

    public void setCurrentPackageId(Long packagesId) {
        this.currentPackageId = packagesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserAccountDTO userAccountDTO = (UserAccountDTO) o;
        if (userAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserAccountDTO{" +
            "id=" + getId() +
            ", remainingChapters=" + getRemainingChapters() +
            ", currPackageStartDate='" + getCurrPackageStartDate() + "'" +
            ", currPackageEndDate='" + getCurrPackageEndDate() + "'" +
            ", userDiscount=" + getUserDiscount() +
            ", activated='" + getActivated() + "'" +
            ", accountType='" + getAccountType() + "'" +
            ", perDayChapterLimit=" + getPerDayChapterLimit() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserEmail() + "'" +
            ", currentPackage=" + getCurrentPackageId() +
            "}";
    }
}
