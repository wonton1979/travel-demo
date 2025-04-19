package dev.group2.traveldiary.travel_diary_backend.seeder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.group2.traveldiary.travel_diary_backend.exception.ContentNotFoundException;
import dev.group2.traveldiary.travel_diary_backend.model.Activity;
import dev.group2.traveldiary.travel_diary_backend.model.Photo;
import dev.group2.traveldiary.travel_diary_backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.List;
import java.io.InputStream;
import java.util.Optional;

@Component
@Order(6)
public class PhotoSeeder implements CommandLineRunner {
    private final PhotoRepository photoRepository;

    private final ActivityRepository activityRepository;

    private final ObjectMapper objectMapper;

    public PhotoSeeder(PhotoRepository photoRepository, ActivityRepository activityRepository,ObjectMapper objectMapper) {
        this.photoRepository = photoRepository;
        this.activityRepository = activityRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (photoRepository.count() == 0) {
            InputStream inputStream = getClass().getResourceAsStream("/photos.json");
            if (inputStream != null) {
                List<Photo> photos = objectMapper.readValue(inputStream, new TypeReference<List<Photo>>(){});
                for (Photo photo : photos) {
                    Long activityId = photo.getActivity().getActivityId();
                    Optional<Activity> optionalActivity = activityRepository.findById(activityId);
                    if (optionalActivity.isPresent()) {
                        photo.setActivity(optionalActivity.get());
                    } else {
                        throw new ContentNotFoundException("Photo not found");
                    }
                }
                photoRepository.saveAll(photos);
                System.out.println("✅ Database seeded with " + photos.size() + " photos");
            } else {
                System.out.println("Could not find photos.json");
            }
        } else {
            System.out.println("ℹ Database already seeded with photos");
        }
    }
}