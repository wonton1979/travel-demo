package dev.group2.traveldiary.travel_diary_backend.service;
import dev.group2.traveldiary.travel_diary_backend.model.Country;
import dev.group2.traveldiary.travel_diary_backend.repository.CountryRepository;
import org.springframework.stereotype.Service;
import dev.group2.traveldiary.travel_diary_backend.exception.ContentNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    public Country createCountry(Country country) {
        return countryRepository.save(country);
    }

    public Country findCountryByName(String name) {
        Optional<Country> optCountry = countryRepository.findByCountryName(name);
        if (optCountry.isPresent()) {
            return optCountry.get();
        }
        else {
            throw new ContentNotFoundException("Country " + name + " not found");
        }
    }
}
