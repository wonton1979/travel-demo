package dev.group2.traveldiary.travel_diary_backend.seeder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.group2.traveldiary.travel_diary_backend.model.Country;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import dev.group2.traveldiary.travel_diary_backend.repository.CountryRepository;

import java.io.InputStream;
import java.util.List;

@Component
@Order(1)
public class CountrySeeder implements CommandLineRunner {
    private final CountryRepository countryRepository;
    private final ObjectMapper objectMapper;

    public CountrySeeder(CountryRepository countryRepository, ObjectMapper objectMapper) {
        this.countryRepository = countryRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception{
        if (countryRepository.count() == 0) {
            InputStream inputStream = this.getClass().getResourceAsStream("/countries.json");
            if (inputStream != null) {
                List<Country> countries = objectMapper.readValue(inputStream, new TypeReference<List<Country>>() {
                });
                countryRepository.saveAll(countries);
                System.out.println("✅ Database seeded with " + countries.size() + " countries");
            }
            else {
                System.out.println("Could not find countries.json");
            }
        }
        else{
            System.out.println("Database already seeded");
        }
    }
}
