package dev.group2.traveldiary.travel_diary_backend.dto;

import dev.group2.traveldiary.travel_diary_backend.model.Itinerary;

import java.time.Instant;

public class ItineraryDTO {
    private final Long userId;
    private final String title;
    private final Long itineraryId;
    private final Boolean isPrivate;
    private final Instant modifiedAt;
    private final long countryId;

    public ItineraryDTO(Itinerary itinerary) {
        this.userId = itinerary.getUser().getUserId();
        this.title = itinerary.getTitle();
        this.itineraryId = itinerary.getItineraryId();
        this.isPrivate = itinerary.getIsPrivate();
        this.modifiedAt = itinerary.getModifiedAt();
        this.countryId = itinerary.getCountry().getCountryId();
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

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public long getCountryId() {
        return countryId;
    }
}
