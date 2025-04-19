package dev.group2.traveldiary.travel_diary_backend.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "itinerary")
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itineraryId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="country_id")
    private Country country;

    @OneToMany(mappedBy = "itinerary",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Activity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "itinerary",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favourite> favourites = new ArrayList<>();

    @NotNull
    private Boolean isPrivate;

    @NotBlank
    private String title;

    @NotNull
    private Instant modifiedAt;

    public Itinerary() {}

    public Itinerary(User user, Country country, Boolean isPrivate, String title, Instant modifiedAt) {
        this.user = user;
        this.country = country;
        this.isPrivate = isPrivate;
        this.title = title;
        this.modifiedAt = modifiedAt;
    }

    public Long getItineraryId() {
        return itineraryId;
    }

    public Country getCountry() {
        return country;
    }
    public Boolean getIsPrivate() {
        return isPrivate;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void setIsPrivate(Boolean privacy) {
        this.isPrivate = privacy;
    }
    public void setCountry(Country country) {
        this.country = country;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public List<Activity> getActivities() {
        return activities;
    }
    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }
    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public List<Favourite> getFavourites() {
        return favourites;
    }
    public void setFavourites(List<Favourite> favourites) {
        this.favourites = favourites;
    }
}
