package dev.group2.traveldiary.travel_diary_backend.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;

    @NotBlank
    @Column(unique = true)
    private String countryName;

    @NotBlank
    private String description;

    @NotBlank
    private String countryPicUrl;

    public Country() {}

    public Country(String countryName, String description, String countryPicUrl) {
        this.countryName = countryName;
        this.description = description;
        this.countryPicUrl = countryPicUrl;
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
    public String getCountryPicUrl() {
        return countryPicUrl;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCountryPicUrl(String photo) {
        this.countryPicUrl = photo;
    }
}
