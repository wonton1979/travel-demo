package dev.group2.traveldiary.travel_diary_backend.service;
import dev.group2.traveldiary.travel_diary_backend.model.Activity;
import dev.group2.traveldiary.travel_diary_backend.model.Itinerary;
import dev.group2.traveldiary.travel_diary_backend.dto.ActivityDTO;
import dev.group2.traveldiary.travel_diary_backend.exception.ContentNotFoundException;
import dev.group2.traveldiary.travel_diary_backend.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final ItineraryService itineraryService;

    public ActivityService(ActivityRepository activityRepository, ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
        this.activityRepository = activityRepository;
    }

    public Activity getActivityEntityById(Long activityId) {
        Optional<Activity> activity = activityRepository.findById(activityId);
        if (activity.isPresent()) {
            return activity.get();
        }
        else {
            throw new ContentNotFoundException("Activity not found");
        }
    }

    public Activity createActivity(Activity activity) {
       Long itineraryId =  activity.getItinerary().getItineraryId();
       Itinerary itinerary = itineraryService.getItineraryEntityById(itineraryId);
       activity.setItinerary(itinerary);
       return activityRepository.save(activity);
    }

    public List<Activity> getActivitiesByItineraryId(Long itineraryId) {
        return activityRepository.getActivitiesByItinerary_ItineraryId(itineraryId);
    }

    public ActivityDTO updateActivity(Long activityId,String title, Boolean completionStatus){
        Optional<Activity> activity = activityRepository.findById(activityId);
        if(activity.isPresent()){
            Activity existingActivity = activity.get();
            if(title != null){
                existingActivity.setTitle(title);
            }
            if(completionStatus != null){
                existingActivity.setCompletionStatus(completionStatus);
            }
            return new ActivityDTO(activityRepository.save(existingActivity));
        }
        else{
            throw new ContentNotFoundException("Activity with id " + activityId + " not found");
        }
    }

    public void deleteActivity(Long activityId) {
        if(activityRepository.existsById(activityId)){
            activityRepository.deleteById(activityId);
        }
        else{
            throw new ContentNotFoundException("Activity with id " + activityId + " not found");
        }
    }

}
