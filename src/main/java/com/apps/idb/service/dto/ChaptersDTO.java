package com.apps.idb.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.apps.idb.domain.enumeration.ChapterStatus;

/**
 * A DTO for the Chapters entity.
 */
public class ChaptersDTO implements Serializable {

    private Long id;

    private ChapterStatus status;

    @NotNull
    private String activated;

    @NotNull
    private LocalDate createdDate;

    @NotNull
    private String createdBy;

    private LocalDate lastUpdatedDate;

    private String lastUpdatedBy;


    private Long initiatorId;

    private String initiatorEmail;

    private Long partnerId;

    private String partnerEmail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChapterStatus getStatus() {
        return status;
    }

    public void setStatus(ChapterStatus status) {
        this.status = status;
    }

    public String getActivated() {
        return activated;
    }

    public void setActivated(String activated) {
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

    public Long getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(Long iDBUserId) {
        this.initiatorId = iDBUserId;
    }

    public String getInitiatorEmail() {
        return initiatorEmail;
    }

    public void setInitiatorEmail(String iDBUserEmail) {
        this.initiatorEmail = iDBUserEmail;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long iDBUserId) {
        this.partnerId = iDBUserId;
    }

    public String getPartnerEmail() {
        return partnerEmail;
    }

    public void setPartnerEmail(String iDBUserEmail) {
        this.partnerEmail = iDBUserEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChaptersDTO chaptersDTO = (ChaptersDTO) o;
        if (chaptersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chaptersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChaptersDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            ", initiator=" + getInitiatorId() +
            ", initiator='" + getInitiatorEmail() + "'" +
            ", partner=" + getPartnerId() +
            ", partner='" + getPartnerEmail() + "'" +
            "}";
    }
}
