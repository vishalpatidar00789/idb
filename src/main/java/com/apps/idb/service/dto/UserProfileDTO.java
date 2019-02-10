package com.apps.idb.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.apps.idb.domain.enumeration.Personalities;
import com.apps.idb.domain.enumeration.Interests;
import com.apps.idb.domain.enumeration.Offerings;
import com.apps.idb.domain.enumeration.ProfileStatus;

/**
 * A DTO for the UserProfile entity.
 */
public class UserProfileDTO implements Serializable {

    private Long id;

    private String screenName;

    @Lob
    private byte[] profilePic;

    private String profilePicContentType;
    @NotNull
    private String gender;

    private String dob;

    @NotNull
    private Integer age;

    @NotNull
    private String country;

    private String state;

    @NotNull
    private String city;

    private String pincode;

    private Personalities personality;

    private Interests interests;

    private Offerings offersings;

    private ProfileStatus status;

    @NotNull
    private LocalDate createdDate;

    @NotNull
    private String createdBy;

    private LocalDate lastUpdatedDate;

    private String lastUpdatedBy;


    private Long userId;

    private String userEmail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public String getProfilePicContentType() {
        return profilePicContentType;
    }

    public void setProfilePicContentType(String profilePicContentType) {
        this.profilePicContentType = profilePicContentType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Personalities getPersonality() {
        return personality;
    }

    public void setPersonality(Personalities personality) {
        this.personality = personality;
    }

    public Interests getInterests() {
        return interests;
    }

    public void setInterests(Interests interests) {
        this.interests = interests;
    }

    public Offerings getOffersings() {
        return offersings;
    }

    public void setOffersings(Offerings offersings) {
        this.offersings = offersings;
    }

    public ProfileStatus getStatus() {
        return status;
    }

    public void setStatus(ProfileStatus status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserProfileDTO userProfileDTO = (UserProfileDTO) o;
        if (userProfileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userProfileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserProfileDTO{" +
            "id=" + getId() +
            ", screenName='" + getScreenName() + "'" +
            ", profilePic='" + getProfilePic() + "'" +
            ", gender='" + getGender() + "'" +
            ", dob='" + getDob() + "'" +
            ", age=" + getAge() +
            ", country='" + getCountry() + "'" +
            ", state='" + getState() + "'" +
            ", city='" + getCity() + "'" +
            ", pincode='" + getPincode() + "'" +
            ", personality='" + getPersonality() + "'" +
            ", interests='" + getInterests() + "'" +
            ", offersings='" + getOffersings() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserEmail() + "'" +
            "}";
    }
}
