package dev.group2.traveldiary.travel_diary_backend.repository;
import dev.group2.traveldiary.travel_diary_backend.model.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {

    List<Favourite> findAllByUser_UserId(Long userUserId);
}
