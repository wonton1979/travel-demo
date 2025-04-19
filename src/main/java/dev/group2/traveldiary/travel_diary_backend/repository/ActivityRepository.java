package dev.group2.traveldiary.travel_diary_backend.repository;
import dev.group2.traveldiary.travel_diary_backend.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ActivityRepository extends JpaRepository <Activity,Long> {
    List<Activity> getActivitiesByItinerary_ItineraryId(Long itineraryId);
}
