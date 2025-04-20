package dev.group2.traveldiary.travel_diary_backend.service;
import org.springframework.stereotype.Service;
import dev.group2.traveldiary.travel_diary_backend.repository.FavouriteRepository;
import java.util.List;

@Service
public class FavouriteService {

    private final FavouriteRepository favouriteRepository;
    public FavouriteService(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }
}
