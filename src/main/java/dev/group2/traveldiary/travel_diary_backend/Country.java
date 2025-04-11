package dev.group2.traveldiary.travel_diary_backend;
import jakarta.persistence.*;

@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;

    private String countryName;
    private String description;
    private String photo;
    public Country() {}
    public Country(String countryName, String description, String photo) {
        this.countryName = countryName;
        this.description = description;
        this.photo = photo;
    }
    public Long getCountryId() {
        return countryId;
    }

    public String getCountryName() {
        return countryName;
    }
    public String getDescription() {
        return description;
    }
    public String getPhoto() {
        return photo;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
