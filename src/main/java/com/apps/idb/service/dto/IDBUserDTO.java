package com.apps.idb.service.dto;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.apps.idb.domain.Authority;
import com.apps.idb.domain.IDBUser;
import com.apps.idb.domain.UserAccount;
import com.apps.idb.domain.UserProfile;
import com.apps.idb.domain.enumeration.UserRoles;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @Size(max = 256)
    private String imageUrl;

    @Size(min = 2, max = 6)
    private String langKey;
    
    private Set<String> authorities;
    
    private String activationKey;

    private String resetKey;

    private LocalDate resetDate = null;
    
    private UserAccount userAccount=null;
    
    private UserProfile userProfile = null;
    
    
    public IDBUserDTO() {
        // Empty constructor needed for Jackson.
    }
    

	public IDBUserDTO(IDBUser user) {
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.activated = user.getActivated();
		this.accessToken = user.getAccessToken();
		this.sessionToken = user.getSessionToken();
		this.lastLoginDate = user.getLastLoginDate();
		this.lastDeactivatedDate = user.getLastDeactivatedDate();
		this.userRoles = user.getUserRoles();
		this.verified = user.isVerified();
		this.verificationMethod = user.getVerificationMethod();
		this.isReportedScam = user.getIsReportedScam();
		this.lastLogout = user.getLastLogout();
		this.lastActivatedDate = user.getLastActivatedDate();
		this.createdDate = user.getCreatedDate();
		this.createdBy = user.getCreatedBy();
		this.lastUpdatedDate = user.getLastUpdatedDate();
		this.lastUpdatedBy = user.getLastUpdatedBy();
		this.imageUrl = user.getImageUrl();
		this.langKey = user.getLangKey();
		this.activationKey = user.getActivationKey();
		this.resetKey = user.getResetKey();
		this.resetDate = user.getResetDate();
		this.userAccount = user.getUserAccount();
		this.userProfile = user.getUserProfile();
		this.authorities = user.getAuthorities().stream()
            .map(Authority::getName)
            .collect(Collectors.toSet());
    }

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
    
    
    
    public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getLangKey() {
		return langKey;
	}

	public void setLangKey(String langKey) {
		this.langKey = langKey;
	}

	public Set<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}

	public Boolean getActivated() {
		return activated;
	}

	public Boolean getVerified() {
		return verified;
	}

	public Boolean getIsReportedScam() {
		return isReportedScam;
	}
	
	

	public String getActivationKey() {
		return activationKey;
	}


	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}


	public String getResetKey() {
		return resetKey;
	}


	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
	}


	public LocalDate getResetDate() {
		return resetDate;
	}


	public void setResetDate(LocalDate resetDate) {
		this.resetDate = resetDate;
	}


	public UserAccount getUserAccount() {
		return userAccount;
	}


	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}


	public UserProfile getUserProfile() {
		return userProfile;
	}


	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
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
		return "IDBUserDTO [id=" + id + ", email=" + email + ", password=" + password + ", activated=" + activated
				+ ", accessToken=" + accessToken + ", sessionToken=" + sessionToken + ", lastLoginDate=" + lastLoginDate
				+ ", lastDeactivatedDate=" + lastDeactivatedDate + ", userRoles=" + userRoles + ", verified=" + verified
				+ ", verificationMethod=" + verificationMethod + ", isReportedScam=" + isReportedScam + ", lastLogout="
				+ lastLogout + ", lastActivatedDate=" + lastActivatedDate + ", createdDate=" + createdDate
				+ ", createdBy=" + createdBy + ", lastUpdatedDate=" + lastUpdatedDate + ", lastUpdatedBy="
				+ lastUpdatedBy + ", imageUrl=" + imageUrl + ", langKey=" + langKey + ", authorities=" + authorities
				+ "]";
	}

   
}
