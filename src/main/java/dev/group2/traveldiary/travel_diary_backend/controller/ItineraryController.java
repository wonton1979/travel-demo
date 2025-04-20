package dev.group2.traveldiary.travel_diary_backend.controller;
import dev.group2.traveldiary.travel_diary_backend.model.Itinerary;
import dev.group2.traveldiary.travel_diary_backend.dto.ItineraryDTO;
import dev.group2.traveldiary.travel_diary_backend.model.User;
import dev.group2.traveldiary.travel_diary_backend.repository.UserRepository;
import dev.group2.traveldiary.travel_diary_backend.service.ItineraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/itineraries")
public class ItineraryController {
    private final ItineraryService itineraryService;
    private final UserRepository userRepository;

    public ItineraryController(ItineraryService itineraryService, UserRepository userRepository) {
        this.itineraryService = itineraryService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Object> addItinerary(@AuthenticationPrincipal UserDetails userDetails,@RequestBody Itinerary itinerary) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation. Please login again."));
        }
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

    @GetMapping("/user/{username}")
    public ResponseEntity<List<ItineraryDTO>> fetchItinerariesByUsername(@PathVariable String username){
        List<ItineraryDTO> itineraryDTOList = itineraryService.getItinerariesByUserName(username);
        return ResponseEntity.status(HttpStatus.OK).body(itineraryDTOList);
    }

    @PatchMapping("/{itineraryId}")
    public ResponseEntity<Object> modifyItinerary(@PathVariable Long itineraryId,
                                                        @RequestBody Itinerary itinerary,
                                                        @AuthenticationPrincipal UserDetails userDetails){
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Long itineraryUserId = itineraryService.getItineraryById(itineraryId).getUserId();
        if(!itineraryUserId.equals(user.getUserId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(itineraryService.updateItinerary(itineraryId,itinerary.getTitle(),itinerary.getIsPrivate()));
    }

    @DeleteMapping( "/{itineraryId}" )
    public ResponseEntity<Map<String,String>> deleteItinerary(@PathVariable Long itineraryId,@AuthenticationPrincipal UserDetails userDetails){
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Long itineraryUserId = itineraryService.getItineraryById(itineraryId).getUserId();
        if(!itineraryUserId.equals(user.getUserId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        itineraryService.deleteItinerary(itineraryId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Itinerary Deleted Successfully"));
    }
}
