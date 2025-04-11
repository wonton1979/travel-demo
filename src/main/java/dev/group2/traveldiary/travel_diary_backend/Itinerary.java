package dev.group2.traveldiary.travel_diary_backend;
import jakarta.persistence.*;

@Entity
@Table(name = "diary")
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    @ManyToOne
    @JoinColumn(name="username")
    private User user;

    @ManyToOne
    @JoinColumn(name="country_id")
    private Country country;

    private Boolean privacy;

    public Itinerary() {}

    public Itinerary(User user, Country country, Boolean privacy) {
        this.user = user;
        this.country = country;
        this.privacy = privacy;
    }

    public Long getDiaryId() {
        return diaryId;
    }

    public Country getCountry() {
        return country;
    }
    public Boolean getPrivacy() {
        return privacy;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void setPrivacy(Boolean privacy) {
        this.privacy = privacy;
    }
    public void setCountry(Country country) {
        this.country = country;
    }
}
