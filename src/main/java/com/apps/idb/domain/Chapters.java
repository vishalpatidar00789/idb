package com.apps.idb.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.apps.idb.domain.enumeration.ChapterStatus;

/**
 * A Chapters.
 */
@Entity
@Table(name = "chapters")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Chapters implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ChapterStatus status;

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

    @ManyToOne
    @JsonIgnoreProperties("initiatedChapters")
    private IDBUser initiator;

    @ManyToOne
    @JsonIgnoreProperties("partnerChapters")
    private IDBUser partner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChapterStatus getStatus() {
        return status;
    }

    public Chapters status(ChapterStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ChapterStatus status) {
        this.status = status;
    }

    public String getActivated() {
        return activated;
    }

    public Chapters activated(String activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Chapters createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Chapters createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Chapters lastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public Chapters lastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public IDBUser getInitiator() {
        return initiator;
    }

    public Chapters initiator(IDBUser iDBUser) {
        this.initiator = iDBUser;
        return this;
    }

    public void setInitiator(IDBUser iDBUser) {
        this.initiator = iDBUser;
    }

    public IDBUser getPartner() {
        return partner;
    }

    public Chapters partner(IDBUser iDBUser) {
        this.partner = iDBUser;
        return this;
    }

    public void setPartner(IDBUser iDBUser) {
        this.partner = iDBUser;
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
        Chapters chapters = (Chapters) o;
        if (chapters.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chapters.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Chapters{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            "}";
    }
}
