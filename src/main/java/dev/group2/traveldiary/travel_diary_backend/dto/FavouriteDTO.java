package dev.group2.traveldiary.travel_diary_backend.dto;

import dev.group2.traveldiary.travel_diary_backend.model.Favourite;

public class FavouriteDTO {
    public final Long itineraryId;
    public final Long userId;
    public final long favouriteId;
    public FavouriteDTO(Favourite favourite) {
        this.itineraryId = favourite.getItinerary().getItineraryId();
        this.userId = favourite.getUser().getUserId();
        this.favouriteId = favourite.getFavouriteId();
    }
    public Long getItineraryId() {
        return itineraryId;
    }
    public Long getUserId() {
        return userId;
    }
    public long getFavouriteId() {
        return favouriteId;
    }
}
