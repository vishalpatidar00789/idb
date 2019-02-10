package com.apps.idb.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.apps.idb.domain.enumeration.AccountType;

/**
 * A UserAccount.
 */
@Entity
@Table(name = "user_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "remaining_chapters", nullable = false)
    private Integer remainingChapters;

    @NotNull
    @Column(name = "curr_package_start_date", nullable = false)
    private LocalDate currPackageStartDate;

    @NotNull
    @Column(name = "curr_package_end_date", nullable = false)
    private LocalDate currPackageEndDate;

    @Column(name = "user_discount")
    private Integer userDiscount;

    @NotNull
    @Column(name = "activated", nullable = false)
    private String activated;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @NotNull
    @Column(name = "per_day_chapter_limit", nullable = false)
    private Integer perDayChapterLimit;

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

    @OneToOne
    @JoinColumn(unique = true)
    private IDBUser user;

    @OneToOne
    @JoinColumn(unique = true)
    private Packages currentPackage;

    @OneToMany(mappedBy = "userAccount")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Payments> payments = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRemainingChapters() {
        return remainingChapters;
    }

    public UserAccount remainingChapters(Integer remainingChapters) {
        this.remainingChapters = remainingChapters;
        return this;
    }

    public void setRemainingChapters(Integer remainingChapters) {
        this.remainingChapters = remainingChapters;
    }

    public LocalDate getCurrPackageStartDate() {
        return currPackageStartDate;
    }

    public UserAccount currPackageStartDate(LocalDate currPackageStartDate) {
        this.currPackageStartDate = currPackageStartDate;
        return this;
    }

    public void setCurrPackageStartDate(LocalDate currPackageStartDate) {
        this.currPackageStartDate = currPackageStartDate;
    }

    public LocalDate getCurrPackageEndDate() {
        return currPackageEndDate;
    }

    public UserAccount currPackageEndDate(LocalDate currPackageEndDate) {
        this.currPackageEndDate = currPackageEndDate;
        return this;
    }

    public void setCurrPackageEndDate(LocalDate currPackageEndDate) {
        this.currPackageEndDate = currPackageEndDate;
    }

    public Integer getUserDiscount() {
        return userDiscount;
    }

    public UserAccount userDiscount(Integer userDiscount) {
        this.userDiscount = userDiscount;
        return this;
    }

    public void setUserDiscount(Integer userDiscount) {
        this.userDiscount = userDiscount;
    }

    public String getActivated() {
        return activated;
    }

    public UserAccount activated(String activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public UserAccount accountType(AccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Integer getPerDayChapterLimit() {
        return perDayChapterLimit;
    }

    public UserAccount perDayChapterLimit(Integer perDayChapterLimit) {
        this.perDayChapterLimit = perDayChapterLimit;
        return this;
    }

    public void setPerDayChapterLimit(Integer perDayChapterLimit) {
        this.perDayChapterLimit = perDayChapterLimit;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public UserAccount createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public UserAccount createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public UserAccount lastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public UserAccount lastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public IDBUser getUser() {
        return user;
    }

    public UserAccount user(IDBUser iDBUser) {
        this.user = iDBUser;
        return this;
    }

    public void setUser(IDBUser iDBUser) {
        this.user = iDBUser;
    }

    public Packages getCurrentPackage() {
        return currentPackage;
    }

    public UserAccount currentPackage(Packages packages) {
        this.currentPackage = packages;
        return this;
    }

    public void setCurrentPackage(Packages packages) {
        this.currentPackage = packages;
    }

    public Set<Payments> getPayments() {
        return payments;
    }

    public UserAccount payments(Set<Payments> payments) {
        this.payments = payments;
        return this;
    }

    public UserAccount addPayments(Payments payments) {
        this.payments.add(payments);
        payments.setUserAccount(this);
        return this;
    }

    public UserAccount removePayments(Payments payments) {
        this.payments.remove(payments);
        payments.setUserAccount(null);
        return this;
    }

    public void setPayments(Set<Payments> payments) {
        this.payments = payments;
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
        UserAccount userAccount = (UserAccount) o;
        if (userAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserAccount{" +
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
            "}";
    }
}
