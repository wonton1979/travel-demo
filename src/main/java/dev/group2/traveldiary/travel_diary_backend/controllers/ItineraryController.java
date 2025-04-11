package dev.group2.traveldiary.travel_diary_backend.controllers;
import dev.group2.traveldiary.travel_diary_backend.Itinerary;
import dev.group2.traveldiary.travel_diary_backend.repository.ItineraryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/itineraries")
public class ItineraryController {
    private final ItineraryRepository itineraryRepository;

    public ItineraryController(ItineraryRepository itineraryRepository) {
        this.itineraryRepository = itineraryRepository;
    }

    @GetMapping
    public List<Itinerary> getItineraries() {
        return itineraryRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Itinerary> addItinerary(@RequestBody Itinerary itinerary) {
       Itinerary savedItinerary = itineraryRepository.save(itinerary);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedItinerary);
    }
}
