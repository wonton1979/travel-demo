package dev.group2.traveldiary.travel_diary_backend.service;
import dev.group2.traveldiary.travel_diary_backend.dto.FavouriteDTO;
import dev.group2.traveldiary.travel_diary_backend.exception.ContentNotFoundException;
import dev.group2.traveldiary.travel_diary_backend.model.Favourite;
import dev.group2.traveldiary.travel_diary_backend.model.Itinerary;
import dev.group2.traveldiary.travel_diary_backend.model.User;
import dev.group2.traveldiary.travel_diary_backend.repository.ItineraryRepository;
import dev.group2.traveldiary.travel_diary_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import dev.group2.traveldiary.travel_diary_backend.repository.FavouriteRepository;
import java.util.List;
import java.util.Optional;

@Service
public class FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final ItineraryRepository itineraryRepository;
    private final UserRepository userRepository;

    public FavouriteService(FavouriteRepository favouriteRepository, ItineraryRepository itineraryRepository, UserRepository userRepository) {
        this.favouriteRepository = favouriteRepository;
        this.itineraryRepository = itineraryRepository;
        this.userRepository = userRepository;
    }

    public List<Favourite> getFavouriteByUserId(Long userId){
        return favouriteRepository.findAllByUser_UserId(userId);
    }

    public void deleteFavourite(Long favouriteId) {
        if(favouriteRepository.existsById(favouriteId)){
            favouriteRepository.deleteById(favouriteId);
        }
        else{
            throw new RuntimeException("Favourite with id " + favouriteId + " not found");
        }
    }

    public FavouriteDTO createFavourite(Favourite favourite) {
        Long itineraryId = favourite.getItinerary().getItineraryId();
        Long userId = favourite.getUser().getUserId();
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isPresent()){
            favourite.setUser(optionalUser.get());
        }
        else{
            throw new ContentNotFoundException("User not found");
        }
        Optional<Itinerary> optionalItinerary = itineraryRepository.findById(itineraryId);
        if (optionalItinerary.isPresent()){
            favourite.setItinerary(optionalItinerary.get());
        }else{
            throw new ContentNotFoundException("Itinerary not found");
        }
        return new FavouriteDTO(favouriteRepository.save(favourite));
    }
}
