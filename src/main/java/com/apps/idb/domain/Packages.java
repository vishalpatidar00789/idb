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

/**
 * A Packages.
 */
@Entity
@Table(name = "packages")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Packages implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "expiry", nullable = false)
    private Integer expiry;

    @NotNull
    @Column(name = "total_chapters", nullable = false)
    private Integer totalChapters;

    @Column(name = "discount")
    private Integer discount;

    @NotNull
    @Column(name = "activated", nullable = false)
    private String activated;

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

    @OneToMany(mappedBy = "appliedPackage")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Payments> payments = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Packages title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public Packages price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public Packages type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getExpiry() {
        return expiry;
    }

    public Packages expiry(Integer expiry) {
        this.expiry = expiry;
        return this;
    }

    public void setExpiry(Integer expiry) {
        this.expiry = expiry;
    }

    public Integer getTotalChapters() {
        return totalChapters;
    }

    public Packages totalChapters(Integer totalChapters) {
        this.totalChapters = totalChapters;
        return this;
    }

    public void setTotalChapters(Integer totalChapters) {
        this.totalChapters = totalChapters;
    }

    public Integer getDiscount() {
        return discount;
    }

    public Packages discount(Integer discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getActivated() {
        return activated;
    }

    public Packages activated(String activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Packages createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Packages createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Packages lastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public Packages lastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Set<Payments> getPayments() {
        return payments;
    }

    public Packages payments(Set<Payments> payments) {
        this.payments = payments;
        return this;
    }

    public Packages addPayments(Payments payments) {
        this.payments.add(payments);
        payments.setAppliedPackage(this);
        return this;
    }

    public Packages removePayments(Payments payments) {
        this.payments.remove(payments);
        payments.setAppliedPackage(null);
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
        Packages packages = (Packages) o;
        if (packages.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), packages.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Packages{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", price=" + getPrice() +
            ", type='" + getType() + "'" +
            ", expiry=" + getExpiry() +
            ", totalChapters=" + getTotalChapters() +
            ", discount=" + getDiscount() +
            ", activated='" + getActivated() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            "}";
    }
}
