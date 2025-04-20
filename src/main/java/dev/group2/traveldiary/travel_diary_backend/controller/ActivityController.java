package dev.group2.traveldiary.travel_diary_backend.controller;
import dev.group2.traveldiary.travel_diary_backend.model.Activity;
import dev.group2.traveldiary.travel_diary_backend.dto.ActivityDTO;
import dev.group2.traveldiary.travel_diary_backend.model.User;
import dev.group2.traveldiary.travel_diary_backend.service.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import dev.group2.traveldiary.travel_diary_backend.repository.UserRepository;
import dev.group2.traveldiary.travel_diary_backend.service.ItineraryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    private final ActivityService activityService;
    private final UserRepository userRepository;
    private final ItineraryService itineraryService;

    public ActivityController(ActivityService activityService, UserRepository userRepository, ItineraryService itineraryService) {
        this.activityService = activityService;
        this.userRepository = userRepository;
        this.itineraryService = itineraryService;
    }

    @PostMapping
    public ResponseEntity<Object> addActivity(@RequestBody Activity activity,@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Long itineraryUserId = itineraryService.getItineraryById(activity.getItinerary().getItineraryId()).getUserId();
        if(!itineraryUserId.equals(user.getUserId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        Activity createdActivity = activityService.createActivity(activity);
        ActivityDTO createdActivityDTO = new ActivityDTO(createdActivity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdActivityDTO);
    }

    @GetMapping("/itinerary/{itineraryId}")
    public ResponseEntity<List<ActivityDTO>> fetchActivityByItineraryId(@PathVariable Long itineraryId) {
         List<ActivityDTO> activitiesDTO = new ArrayList<>();
         List<Activity> activities = activityService.getActivitiesByItineraryId(itineraryId);
         for (Activity activity : activities) {
             activitiesDTO.add(new ActivityDTO(activity));
         }
         return ResponseEntity.status(HttpStatus.OK).body(activitiesDTO);
    }

    @PatchMapping("/{activityId}")
    public ResponseEntity<Object> modifyActivity(@PathVariable Long activityId,
                                                      @AuthenticationPrincipal UserDetails userDetails,
                                                      @RequestBody Activity activity) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Activity currentActivity = activityService.getActivityEntityById(activityId);
        Long itineraryUserId = itineraryService.getItineraryById(currentActivity.getItinerary().getItineraryId()).getUserId();
        if(!itineraryUserId.equals(user.getUserId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
       return ResponseEntity.status(HttpStatus.OK).body(activityService.updateActivity(activityId,activity.getTitle(),activity.getCompletionStatus()));
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<Map<String,String>> deleteActivity(@PathVariable Long activityId,@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Activity currentActivity = activityService.getActivityEntityById(activityId);
        Long itineraryUserId = itineraryService.getItineraryById(currentActivity.getItinerary().getItineraryId()).getUserId();
        if(!itineraryUserId.equals(user.getUserId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        activityService.deleteActivity(activityId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Activity Deleted Successfully"));
    }

}
