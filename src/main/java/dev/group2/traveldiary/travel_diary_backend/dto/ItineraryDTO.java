package dev.group2.traveldiary.travel_diary_backend.dto;

import dev.group2.traveldiary.travel_diary_backend.model.Itinerary;

public class ItineraryDTO {
    private final Long userId;
    private final String title;
    private final Long itineraryId;

    public ItineraryDTO(Itinerary itinerary) {
        this.userId = itinerary.getUser().getUserId();
        this.title = itinerary.getTitle();
        this.itineraryId = itinerary.getItineraryId();
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public Long getItineraryId() {
        return itineraryId;
    }
}
