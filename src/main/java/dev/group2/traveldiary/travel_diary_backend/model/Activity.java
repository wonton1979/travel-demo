package dev.group2.traveldiary.travel_diary_backend.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;

    @ManyToOne
    @JoinColumn(name="itinerary_id")
    private Itinerary itinerary;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos = new ArrayList<>();

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes = new ArrayList<>();

    @NotBlank
    private String title;

    @NotNull
    private Boolean completionStatus;

    public Activity() {}

    public Activity(Itinerary itinerary, String title, Boolean completionStatus) {
        this.itinerary = itinerary;
        this.title = title;
        this.completionStatus = completionStatus;
    }


    public Long getActivityId() {
        return activityId;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }
    public String getTitle() {
        return title;
    }
    public Boolean getCompletionStatus() {
        return completionStatus;
    }

    @JsonProperty("activityId")
    public void setActivityId(Long activityId) {this.activityId = activityId;}

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setCompletionStatus(Boolean completionStatus) {
        this.completionStatus = completionStatus;
    }

    public List<Photo> getPhotos() {
        return photos;
    }
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Note> getNotes() {
        return notes;
    }
    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
