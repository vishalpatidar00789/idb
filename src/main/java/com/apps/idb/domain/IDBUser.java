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

import com.apps.idb.domain.enumeration.UserRoles;

/**
 * A IDBUser.
 */
@Entity
@Table(name = "idb_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IDBUser implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "jhi_password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "activated", nullable = false)
    private Boolean activated;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "session_token")
    private String sessionToken;

    @Column(name = "last_login_date")
    private LocalDate lastLoginDate;

    @Column(name = "last_deactivated_date")
    private LocalDate lastDeactivatedDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_roles", nullable = false)
    private UserRoles userRoles;

    @Column(name = "verified")
    private Boolean verified;

    @Column(name = "verification_method")
    private String verificationMethod;

    @Column(name = "is_reported_scam")
    private Boolean isReportedScam;

    @Column(name = "last_logout")
    private LocalDate lastLogout;

    @Column(name = "last_activated_date")
    private LocalDate lastActivatedDate;

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

    @OneToMany(mappedBy = "initiator")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Chapters> initiatedChapters = new HashSet<>();
    @OneToMany(mappedBy = "partner")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Chapters> partnerChapters = new HashSet<>();
    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private UserProfile userProfile;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private UserAccount userAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public IDBUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public IDBUser password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isActivated() {
        return activated;
    }

    public IDBUser activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public IDBUser accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public IDBUser sessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
        return this;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public LocalDate getLastLoginDate() {
        return lastLoginDate;
    }

    public IDBUser lastLoginDate(LocalDate lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
        return this;
    }

    public void setLastLoginDate(LocalDate lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public LocalDate getLastDeactivatedDate() {
        return lastDeactivatedDate;
    }

    public IDBUser lastDeactivatedDate(LocalDate lastDeactivatedDate) {
        this.lastDeactivatedDate = lastDeactivatedDate;
        return this;
    }

    public void setLastDeactivatedDate(LocalDate lastDeactivatedDate) {
        this.lastDeactivatedDate = lastDeactivatedDate;
    }

    public UserRoles getUserRoles() {
        return userRoles;
    }

    public IDBUser userRoles(UserRoles userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public void setUserRoles(UserRoles userRoles) {
        this.userRoles = userRoles;
    }

    public Boolean isVerified() {
        return verified;
    }

    public IDBUser verified(Boolean verified) {
        this.verified = verified;
        return this;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getVerificationMethod() {
        return verificationMethod;
    }

    public IDBUser verificationMethod(String verificationMethod) {
        this.verificationMethod = verificationMethod;
        return this;
    }

    public void setVerificationMethod(String verificationMethod) {
        this.verificationMethod = verificationMethod;
    }

    public Boolean isIsReportedScam() {
        return isReportedScam;
    }

    public IDBUser isReportedScam(Boolean isReportedScam) {
        this.isReportedScam = isReportedScam;
        return this;
    }

    public void setIsReportedScam(Boolean isReportedScam) {
        this.isReportedScam = isReportedScam;
    }

    public LocalDate getLastLogout() {
        return lastLogout;
    }

    public IDBUser lastLogout(LocalDate lastLogout) {
        this.lastLogout = lastLogout;
        return this;
    }

    public void setLastLogout(LocalDate lastLogout) {
        this.lastLogout = lastLogout;
    }

    public LocalDate getLastActivatedDate() {
        return lastActivatedDate;
    }

    public IDBUser lastActivatedDate(LocalDate lastActivatedDate) {
        this.lastActivatedDate = lastActivatedDate;
        return this;
    }

    public void setLastActivatedDate(LocalDate lastActivatedDate) {
        this.lastActivatedDate = lastActivatedDate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public IDBUser createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public IDBUser createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public IDBUser lastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public IDBUser lastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Set<Chapters> getInitiatedChapters() {
        return initiatedChapters;
    }

    public IDBUser initiatedChapters(Set<Chapters> chapters) {
        this.initiatedChapters = chapters;
        return this;
    }

    public IDBUser addInitiatedChapters(Chapters chapters) {
        this.initiatedChapters.add(chapters);
        chapters.setInitiator(this);
        return this;
    }

    public IDBUser removeInitiatedChapters(Chapters chapters) {
        this.initiatedChapters.remove(chapters);
        chapters.setInitiator(null);
        return this;
    }

    public void setInitiatedChapters(Set<Chapters> chapters) {
        this.initiatedChapters = chapters;
    }

    public Set<Chapters> getPartnerChapters() {
        return partnerChapters;
    }

    public IDBUser partnerChapters(Set<Chapters> chapters) {
        this.partnerChapters = chapters;
        return this;
    }

    public IDBUser addPartnerChapters(Chapters chapters) {
        this.partnerChapters.add(chapters);
        chapters.setPartner(this);
        return this;
    }

    public IDBUser removePartnerChapters(Chapters chapters) {
        this.partnerChapters.remove(chapters);
        chapters.setPartner(null);
        return this;
    }

    public void setPartnerChapters(Set<Chapters> chapters) {
        this.partnerChapters = chapters;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public IDBUser userProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        return this;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public IDBUser userAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
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
        IDBUser iDBUser = (IDBUser) o;
        if (iDBUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iDBUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IDBUser{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", activated='" + isActivated() + "'" +
            ", accessToken='" + getAccessToken() + "'" +
            ", sessionToken='" + getSessionToken() + "'" +
            ", lastLoginDate='" + getLastLoginDate() + "'" +
            ", lastDeactivatedDate='" + getLastDeactivatedDate() + "'" +
            ", userRoles='" + getUserRoles() + "'" +
            ", verified='" + isVerified() + "'" +
            ", verificationMethod='" + getVerificationMethod() + "'" +
            ", isReportedScam='" + isIsReportedScam() + "'" +
            ", lastLogout='" + getLastLogout() + "'" +
            ", lastActivatedDate='" + getLastActivatedDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            "}";
    }
}
