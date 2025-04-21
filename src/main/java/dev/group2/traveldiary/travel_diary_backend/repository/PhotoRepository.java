package dev.group2.traveldiary.travel_diary_backend.repository;
import dev.group2.traveldiary.travel_diary_backend.model.Note;
import dev.group2.traveldiary.travel_diary_backend.model.Photo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Optional<List<Photo>> findByActivity_ActivityId(Long activityId);

    Note getPhotoByPhotoId(Long photoId);
}
