package dev.group2.traveldiary.travel_diary_backend.service;
import dev.group2.traveldiary.travel_diary_backend.model.Itinerary;
import dev.group2.traveldiary.travel_diary_backend.exception.ContentNotFoundException;
import dev.group2.traveldiary.travel_diary_backend.repository.ItineraryRepository;
import dev.group2.traveldiary.travel_diary_backend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import dev.group2.traveldiary.travel_diary_backend.dto.ItineraryDTO;
import dev.group2.traveldiary.travel_diary_backend.model.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final UserRepository userRepository;

    public ItineraryService(ItineraryRepository itineraryRepository, UserRepository userRepository) {
        this.itineraryRepository = itineraryRepository;
        this.userRepository = userRepository;
    }

    public Itinerary addItinerary(@RequestBody Itinerary itinerary) {
        Long userId = itinerary.getUser().getUserId();
        Optional<User> itineraryUser = userRepository.findByUserId(userId);
        if (itineraryUser.isPresent()) {
            itinerary.setUser(itineraryUser.get());
            return itineraryRepository.save(itinerary);
        }
        else{
            throw new ContentNotFoundException("User not found");
        }
    }

    public ItineraryDTO getItineraryById(Long itineraryId) {
        Optional<Itinerary> optItinerary = itineraryRepository.findById(itineraryId);
        if (optItinerary.isPresent()) {
            return new ItineraryDTO(optItinerary.get());
        }
        else{
            throw new RuntimeException("Itinerary not found");
        }
    }

    public Itinerary getItineraryEntityById(Long itineraryId) {
        Optional<Itinerary> itinerary = itineraryRepository.findById(itineraryId);
        if (itinerary.isPresent()) {
            return itinerary.get();
        }
        else{
            throw new RuntimeException("Itinerary not found");
        }
    }

    public Page<ItineraryDTO> getItinerariesByCountryName(String countryName, Pageable pageable) {
        return itineraryRepository.findAllByCountry_CountryName(countryName, pageable)
                .map(itinerary -> new ItineraryDTO(itinerary));
    }

    public Page<ItineraryDTO> getItinerariesByUserName(String username,Pageable pageable) {
        return itineraryRepository.findAllByUser_Username(username,pageable)
                .map(itinerary -> new ItineraryDTO(itinerary));
    }

    public List<Itinerary> getItinerariesByUserName(String username) {
        return itineraryRepository.findAllByUser_Username(username);
    }

    public ItineraryDTO updateItinerary(Long itineraryId,String title, Boolean isPrivate){
        Optional<Itinerary> itinerary = itineraryRepository.findById(itineraryId);
        if(itinerary.isPresent()){
            Itinerary existingItinerary = itinerary.get();
            if(title != null){
                existingItinerary.setTitle(title);
            }
            if(isPrivate != null){
                existingItinerary.setIsPrivate(isPrivate);
            }
            existingItinerary.setModifiedAt(Instant.now());
            return new ItineraryDTO(itineraryRepository.save(existingItinerary));
        }
        else{
            throw new ContentNotFoundException("Itinerary with id " + itineraryId + " not found");
        }
    }

    public void deleteItinerary(Long itineraryId) {
        if(itineraryRepository.existsById(itineraryId)){
            itineraryRepository.deleteById(itineraryId);
        }
        else{
            throw new RuntimeException("Itinerary with id " + itineraryId + " not found");
        }
    }
}
