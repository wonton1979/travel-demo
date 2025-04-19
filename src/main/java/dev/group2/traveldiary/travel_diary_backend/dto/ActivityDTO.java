package dev.group2.traveldiary.travel_diary_backend.dto;

import dev.group2.traveldiary.travel_diary_backend.model.Activity;

public class ActivityDTO {
    private final Long activityId;
    private final String title;
    private final Long itineraryId;
    private final Boolean completionStatus;

    public ActivityDTO(Activity activity) {
        this.activityId = activity.getActivityId();
        this.title = activity.getTitle();
        this.itineraryId = activity.getItinerary().getItineraryId();
        this.completionStatus = activity.getCompletionStatus();
    }
    public Long getActivityId() {
        return activityId;
    }
    public String getTitle() {
        return title;
    }
    public Long getItineraryId() {
        return itineraryId;
    }
    public Boolean getCompletionStatus() {
        return completionStatus;
    }
}
