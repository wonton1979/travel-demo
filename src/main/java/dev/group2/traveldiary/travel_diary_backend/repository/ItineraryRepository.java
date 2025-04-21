package dev.group2.traveldiary.travel_diary_backend.repository;
import dev.group2.traveldiary.travel_diary_backend.model.Itinerary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItineraryRepository extends JpaRepository<Itinerary,Long> {
    Page<Itinerary> findAllByCountry_CountryName(String countryName, Pageable pageable);
    Page<Itinerary> findAllByUser_Username(String username,Pageable pageable);

    List<Itinerary> findAllByUser_Username(String username);
}
