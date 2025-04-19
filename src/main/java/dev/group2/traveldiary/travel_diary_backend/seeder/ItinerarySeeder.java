package dev.group2.traveldiary.travel_diary_backend.seeder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.group2.traveldiary.travel_diary_backend.model.Country;
import dev.group2.traveldiary.travel_diary_backend.model.Itinerary;
import dev.group2.traveldiary.travel_diary_backend.exception.ContentNotFoundException;
import dev.group2.traveldiary.travel_diary_backend.repository.ItineraryRepository;
import dev.group2.traveldiary.travel_diary_backend.repository.CountryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Component
@Order(3)
public class ItinerarySeeder implements CommandLineRunner {
    private final ItineraryRepository itineraryRepository;
    private final CountryRepository countryRepository;
    private final ObjectMapper objectMapper;

    public ItinerarySeeder(ItineraryRepository itineraryRepository, ObjectMapper objectMapper, CountryRepository countryRepository) {
        this.itineraryRepository = itineraryRepository;
        this.objectMapper = objectMapper;
        this.countryRepository = countryRepository;
    }

    @Override
    public void run(String... args) throws Exception{
        if (itineraryRepository.count() == 0) {
            InputStream inputStream = this.getClass().getResourceAsStream("/itineraries.json");
            if (inputStream != null) {
                List<Itinerary> itineraries = objectMapper.readValue(inputStream, new TypeReference<List<Itinerary>>() {
                });
                for(Itinerary itinerary : itineraries){
                    Long countryId = itinerary.getCountry().getCountryId();
                    Optional<Country> country = countryRepository.findById(countryId);
                    if(country.isPresent()){
                        itinerary.setCountry(country.get());
                    }
                    else{
                        throw new ContentNotFoundException("Country with id " + countryId + " not found");
                    }
                }
                itineraryRepository.saveAll(itineraries);
                System.out.println("✅ Database seeded with " + itineraries.size() + " itineraries");
            }
            else {
                System.out.println("Could not find itineraries.json");
            }
        }
        else{
            System.out.println("Database already seeded");
        }
    }
}