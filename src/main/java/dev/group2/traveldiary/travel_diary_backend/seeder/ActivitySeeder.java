package dev.group2.traveldiary.travel_diary_backend.seeder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.group2.traveldiary.travel_diary_backend.model.Itinerary;
import dev.group2.traveldiary.travel_diary_backend.model.Activity;
import dev.group2.traveldiary.travel_diary_backend.exception.ContentNotFoundException;
import dev.group2.traveldiary.travel_diary_backend.repository.ItineraryRepository;
import dev.group2.traveldiary.travel_diary_backend.repository.ActivityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Component
@Order(4)
public class ActivitySeeder implements CommandLineRunner {
    private final ItineraryRepository itineraryRepository;
    private final ActivityRepository activityRepository;
    private final ObjectMapper objectMapper;

    public ActivitySeeder(ItineraryRepository itineraryRepository, ObjectMapper objectMapper, ActivityRepository activityRepository) {
        this.itineraryRepository = itineraryRepository;
        this.objectMapper = objectMapper;
        this.activityRepository = activityRepository;
    }

    @Override
    public void run(String... args) throws Exception{
        if (activityRepository.count() == 0) {
            InputStream inputStream = this.getClass().getResourceAsStream("/activities.json");
            if (inputStream != null) {
                List<Activity> activities = objectMapper.readValue(inputStream, new TypeReference<List<Activity>>() {
                });
                for(Activity activity : activities){
                    Long itineraryId = activity.getItinerary().getItineraryId();
                    Optional<Itinerary> itinerary = itineraryRepository.findById(itineraryId);
                    if(itinerary.isPresent()){
                        activity.setItinerary(itinerary.get());
                    }
                    else{
                        throw new ContentNotFoundException("Itinerary with id " + itineraryId + " not found");
                    }
                }
                activityRepository.saveAll(activities);
                System.out.println("✅ Database seeded with " + activities.size() + " activities");
            }
            else {
                System.out.println("Could not find activities.json");
            }
        }
        else{
            System.out.println("Database already seeded");
        }
    }
}