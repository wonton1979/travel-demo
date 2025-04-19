package dev.group2.traveldiary.travel_diary_backend.dto;

import dev.group2.traveldiary.travel_diary_backend.model.Photo;

import java.time.Instant;

public class PhotoDTO {
    private final Long activityId;
    private final String caption;
    private final Instant createdAt;
    public PhotoDTO(Photo photo){
        this.activityId = photo.getActivity().getActivityId();
        this.caption = photo.getCaption();
        this.createdAt = photo.getModifiedAt();
    }

    public Long getActivityId() {
        return activityId;
    }
    public String getCaption() {
        return caption;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
}