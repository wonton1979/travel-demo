package dev.group2.traveldiary.travel_diary_backend;
import jakarta.persistence.*;

@Entity
@Table(name="favourites")
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int favouriteId;

    @OneToOne
    @JoinColumn(name="diary_id")
    private Itinerary itinerary;

    public Favourite() {}

    public Favourite(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public Itinerary getDiary() {
        return itinerary;
    }
    public void setDiary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }
}
