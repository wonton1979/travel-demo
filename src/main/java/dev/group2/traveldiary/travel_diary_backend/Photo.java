package dev.group2.traveldiary.travel_diary_backend;
import jakarta.persistence.*;

@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    @ManyToOne
    @JoinColumn(name="activity_id")
    private Activity activity;

    private String caption;
    private String photoUrl;

    public Photo() {}
    public Photo(Activity activity, String caption, String photoUrl) {
        this.activity = activity;
        this.caption = caption;
        this.photoUrl = photoUrl;
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
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
