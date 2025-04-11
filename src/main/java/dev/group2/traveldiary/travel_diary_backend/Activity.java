package dev.group2.traveldiary.travel_diary_backend;
import jakarta.persistence.*;

@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;

    @ManyToOne
    @JoinColumn(name="diary_id")
    private Itinerary itinerary;

    private String title;
    private String description;
    private String completionStatus;

    public Activity() {}

    public Activity(Itinerary itinerary, String title, String description, String completionStatus) {
        this.itinerary = itinerary;
        this.title = title;
        this.description = description;
        this.completionStatus = completionStatus;
    }

    public Long getActivityId() {
        return activityId;
    }

    public Itinerary getDiary() {
        return itinerary;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getCompletionStatus() {
        return completionStatus;
    }

    public void setDiary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCompletionStatus(String completionStatus) {
        this.completionStatus = completionStatus;
    }
}
