package dev.group2.traveldiary.travel_diary_backend.controller;
import dev.group2.traveldiary.travel_diary_backend.model.Itinerary;
import dev.group2.traveldiary.travel_diary_backend.dto.ItineraryDTO;
import dev.group2.traveldiary.travel_diary_backend.service.ItineraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/itineraries")
public class ItineraryController {
    private final ItineraryService itineraryService;

    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @PostMapping
    public ResponseEntity<ItineraryDTO> addItinerary(@RequestBody Itinerary itinerary) {
       Itinerary savedItinerary = itineraryService.addItinerary(itinerary);
       ItineraryDTO savedItineraryDTO = new ItineraryDTO(savedItinerary);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedItineraryDTO);
    }

    @GetMapping("/country/{countryName}")
    public ResponseEntity <List<ItineraryDTO>> fetchItinerariesByCountryName(@PathVariable String countryName){
        List<ItineraryDTO> itinerariesResult = itineraryService.getItinerariesByCountryName(countryName);
        return ResponseEntity.status(HttpStatus.OK).body(itinerariesResult);
    }

    @GetMapping("/{itineraryId}")
    public ResponseEntity<ItineraryDTO> fetchItineraryById(@PathVariable Long itineraryId){
        ItineraryDTO itineraryInfo = itineraryService.getItineraryById(itineraryId);
        return ResponseEntity.status(HttpStatus.OK).body(itineraryInfo);
    }

    @GetMapping
    public ResponseEntity<List<ItineraryDTO>> fetchItinerariesByUsername(@RequestParam("username") String username){
        List<ItineraryDTO> itineraryDTOList = itineraryService.getItinerariesByUserName(username);
        return ResponseEntity.status(HttpStatus.OK).body(itineraryDTOList);
    }

    @PatchMapping("/{itineraryId}")
    public ResponseEntity<ItineraryDTO> modifyItinerary(@PathVariable Long itineraryId,
                                                        @RequestParam(required = false) String title,
                                                        @RequestParam(required = false) Boolean isPrivate){
        return ResponseEntity.status(HttpStatus.OK).body(itineraryService.updateItinerary(itineraryId,title,isPrivate));
    }

    @DeleteMapping( "/{itineraryId}" )
    public ResponseEntity<Map<String,String>> deleteItinerary(@PathVariable Long itineraryId){
        itineraryService.deleteItinerary(itineraryId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Itinerary Deleted Successfully"));
    }
}
