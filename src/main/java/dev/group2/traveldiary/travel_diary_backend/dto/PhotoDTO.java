package dev.group2.traveldiary.travel_diary_backend.dto;

import dev.group2.traveldiary.travel_diary_backend.model.Photo;

import java.time.Instant;

public class PhotoDTO {
    private final Long activityId;
    private final String caption;
    private final String imgUrl;
    private final Instant createdAt;
    private final long photoId;
    public PhotoDTO(Photo photo){
        this.activityId = photo.getActivity().getActivityId();
        this.caption = photo.getCaption();
        this.createdAt = photo.getModifiedAt();
        this.imgUrl = photo.getImgUrl();
        this.photoId = photo.getPhotoId();
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
    public String getImgUrl() {
        return imgUrl;
    }
    public long getPhotoId() {
        return photoId;
    }
}