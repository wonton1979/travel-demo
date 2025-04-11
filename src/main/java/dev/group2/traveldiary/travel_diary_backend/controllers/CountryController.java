package dev.group2.traveldiary.travel_diary_backend.controllers;
import dev.group2.traveldiary.travel_diary_backend.Country;
import dev.group2.traveldiary.travel_diary_backend.repository.CountryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    private final CountryRepository countryRepository;

    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping
    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Country> addCountry(@RequestBody Country country) {
        Country savedCountry = countryRepository.save(country);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCountry);
    }
}
