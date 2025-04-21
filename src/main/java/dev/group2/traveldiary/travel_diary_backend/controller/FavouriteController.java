package dev.group2.traveldiary.travel_diary_backend.controller;

import dev.group2.traveldiary.travel_diary_backend.dto.FavouriteDTO;
import dev.group2.traveldiary.travel_diary_backend.model.Favourite;
import dev.group2.traveldiary.travel_diary_backend.model.User;
import dev.group2.traveldiary.travel_diary_backend.repository.FavouriteRepository;
import dev.group2.traveldiary.travel_diary_backend.repository.UserRepository;
import dev.group2.traveldiary.travel_diary_backend.service.FavouriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favourites")
public class FavouriteController {

    private final UserRepository userRepository;
    private final FavouriteService favouriteService;
    private final FavouriteRepository favouriteRepository;

    public FavouriteController(FavouriteService favouriteService, UserRepository userRepository, FavouriteRepository favouriteRepository) {
        this.favouriteService = favouriteService;
        this.userRepository = userRepository;
        this.favouriteRepository = favouriteRepository;
    }

    @GetMapping
    public ResponseEntity<Object> getFavourites(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        List<Favourite> favourites = favouriteService.getFavouriteByUserId(user.getUserId());
        List<FavouriteDTO> favouriteDTOList = new ArrayList<>();
        for (Favourite favourite : favourites) {
            favouriteDTOList.add(new FavouriteDTO(favourite));
        }
        return ResponseEntity.status(HttpStatus.OK).body(favouriteDTOList);
    }

    @PostMapping
    public ResponseEntity<Object>  addFavourite(@RequestBody Favourite favourite,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        if(!favourite.getUser().getUserId().equals(user.getUserId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(favouriteService.createFavourite(favourite));
    }

    @DeleteMapping("/{favouriteId}")
    public ResponseEntity<Map<String,String>> deleteFavourite(@PathVariable Long favouriteId,@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        User loggedInUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        User favouriteUser = favouriteRepository.findById(favouriteId).orElseThrow().getUser();
        if(!favouriteUser.getUserId().equals(loggedInUser.getUserId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        favouriteService.deleteFavourite(favouriteId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User deleted successfully."));
    }
}
