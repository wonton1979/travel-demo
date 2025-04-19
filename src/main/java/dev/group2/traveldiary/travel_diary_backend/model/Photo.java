package dev.group2.traveldiary.travel_diary_backend.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;

@Entity
@Table(name="photos")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    private String caption;

    @URL
    @NotBlank
    private String imgUrl;

    private Instant modifiedAt;

    public Photo() {}

    public Photo(Activity activity, String caption, String imgUrl, Instant modifiedAt) {
        this.activity = activity;
        this.caption = caption;
        this.imgUrl = imgUrl;
        this.modifiedAt = modifiedAt;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public Activity getActivity() {
        return activity;
    }

    public String getCaption() {
        return caption;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

}