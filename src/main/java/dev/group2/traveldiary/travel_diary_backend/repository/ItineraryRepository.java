package dev.group2.traveldiary.travel_diary_backend.repository;
import dev.group2.traveldiary.travel_diary_backend.model.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItineraryRepository extends JpaRepository<Itinerary,Long> {
    List<Itinerary> findAllByCountry_CountryName(String countryName);
    List<Itinerary> findAllByUser_Username(String username);
}
