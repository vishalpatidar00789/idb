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

import com.apps.idb.domain.enumeration.Personalities;

import com.apps.idb.domain.enumeration.Interests;

import com.apps.idb.domain.enumeration.Offerings;

import com.apps.idb.domain.enumeration.ProfileStatus;

/**
 * A UserProfile.
 */
@Entity
@Table(name = "user_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "screen_name")
    private String screenName;

    @Lob
    @Column(name = "profile_pic")
    private byte[] profilePic;

    @Column(name = "profile_pic_content_type")
    private String profilePicContentType;

    @NotNull
    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "dob")
    private LocalDate dob;

    @NotNull
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull
    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "state")
    private String state;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "pincode")
    private String pincode;

    @Enumerated(EnumType.STRING)
    @Column(name = "personality")
    private Personalities personality;

    @Enumerated(EnumType.STRING)
    @Column(name = "interests")
    private Interests interests;

    @Enumerated(EnumType.STRING)
    @Column(name = "offersings")
    private Offerings offersings;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProfileStatus status;

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

    @OneToMany(mappedBy = "userProfile")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Photos> pics = new HashSet<>();
    @OneToMany(mappedBy = "userProfile")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProfileSettings> profileSettings = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScreenName() {
        return screenName;
    }

    public UserProfile screenName(String screenName) {
        this.screenName = screenName;
        return this;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public UserProfile profilePic(byte[] profilePic) {
        this.profilePic = profilePic;
        return this;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public String getProfilePicContentType() {
        return profilePicContentType;
    }

    public UserProfile profilePicContentType(String profilePicContentType) {
        this.profilePicContentType = profilePicContentType;
        return this;
    }

    public void setProfilePicContentType(String profilePicContentType) {
        this.profilePicContentType = profilePicContentType;
    }

    public String getGender() {
        return gender;
    }

    public UserProfile gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public UserProfile dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Integer getAge() {
        return age;
    }

    public UserProfile age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public UserProfile country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public UserProfile state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public UserProfile city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public UserProfile pincode(String pincode) {
        this.pincode = pincode;
        return this;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Personalities getPersonality() {
        return personality;
    }

    public UserProfile personality(Personalities personality) {
        this.personality = personality;
        return this;
    }

    public void setPersonality(Personalities personality) {
        this.personality = personality;
    }

    public Interests getInterests() {
        return interests;
    }

    public UserProfile interests(Interests interests) {
        this.interests = interests;
        return this;
    }

    public void setInterests(Interests interests) {
        this.interests = interests;
    }

    public Offerings getOffersings() {
        return offersings;
    }

    public UserProfile offersings(Offerings offersings) {
        this.offersings = offersings;
        return this;
    }

    public void setOffersings(Offerings offersings) {
        this.offersings = offersings;
    }

    public ProfileStatus getStatus() {
        return status;
    }

    public UserProfile status(ProfileStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ProfileStatus status) {
        this.status = status;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public UserProfile createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public UserProfile createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public UserProfile lastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public UserProfile lastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public IDBUser getUser() {
        return user;
    }

    public UserProfile user(IDBUser iDBUser) {
        this.user = iDBUser;
        return this;
    }

    public void setUser(IDBUser iDBUser) {
        this.user = iDBUser;
    }

    public Set<Photos> getPics() {
        return pics;
    }

    public UserProfile pics(Set<Photos> photos) {
        this.pics = photos;
        return this;
    }

    public UserProfile addPics(Photos photos) {
        this.pics.add(photos);
        photos.setUserProfile(this);
        return this;
    }

    public UserProfile removePics(Photos photos) {
        this.pics.remove(photos);
        photos.setUserProfile(null);
        return this;
    }

    public void setPics(Set<Photos> photos) {
        this.pics = photos;
    }

    public Set<ProfileSettings> getProfileSettings() {
        return profileSettings;
    }

    public UserProfile profileSettings(Set<ProfileSettings> profileSettings) {
        this.profileSettings = profileSettings;
        return this;
    }

    public UserProfile addProfileSettings(ProfileSettings profileSettings) {
        this.profileSettings.add(profileSettings);
        profileSettings.setUserProfile(this);
        return this;
    }

    public UserProfile removeProfileSettings(ProfileSettings profileSettings) {
        this.profileSettings.remove(profileSettings);
        profileSettings.setUserProfile(null);
        return this;
    }

    public void setProfileSettings(Set<ProfileSettings> profileSettings) {
        this.profileSettings = profileSettings;
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
        UserProfile userProfile = (UserProfile) o;
        if (userProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserProfile{" +
            "id=" + getId() +
            ", screenName='" + getScreenName() + "'" +
            ", profilePic='" + getProfilePic() + "'" +
            ", profilePicContentType='" + getProfilePicContentType() + "'" +
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
            "}";
    }
}
