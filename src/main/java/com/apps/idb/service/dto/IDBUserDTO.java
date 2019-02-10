package com.apps.idb.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.apps.idb.domain.enumeration.UserRoles;

/**
 * A DTO for the IDBUser entity.
 */
public class IDBUserDTO implements Serializable {

    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private Boolean activated;

    private String accessToken;

    private String sessionToken;

    private LocalDate lastLoginDate;

    private LocalDate lastDeactivatedDate;

    @NotNull
    private UserRoles userRoles;

    private Boolean verified;

    private String verificationMethod;

    private Boolean isReportedScam;

    private LocalDate lastLogout;

    private LocalDate lastActivatedDate;

    @NotNull
    private LocalDate createdDate;

    @NotNull
    private String createdBy;

    private LocalDate lastUpdatedDate;

    private String lastUpdatedBy;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public LocalDate getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDate lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public LocalDate getLastDeactivatedDate() {
        return lastDeactivatedDate;
    }

    public void setLastDeactivatedDate(LocalDate lastDeactivatedDate) {
        this.lastDeactivatedDate = lastDeactivatedDate;
    }

    public UserRoles getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(UserRoles userRoles) {
        this.userRoles = userRoles;
    }

    public Boolean isVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getVerificationMethod() {
        return verificationMethod;
    }

    public void setVerificationMethod(String verificationMethod) {
        this.verificationMethod = verificationMethod;
    }

    public Boolean isIsReportedScam() {
        return isReportedScam;
    }

    public void setIsReportedScam(Boolean isReportedScam) {
        this.isReportedScam = isReportedScam;
    }

    public LocalDate getLastLogout() {
        return lastLogout;
    }

    public void setLastLogout(LocalDate lastLogout) {
        this.lastLogout = lastLogout;
    }

    public LocalDate getLastActivatedDate() {
        return lastActivatedDate;
    }

    public void setLastActivatedDate(LocalDate lastActivatedDate) {
        this.lastActivatedDate = lastActivatedDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IDBUserDTO iDBUserDTO = (IDBUserDTO) o;
        if (iDBUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iDBUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IDBUserDTO{" +
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
