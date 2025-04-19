package dev.group2.traveldiary.travel_diary_backend.controller;
import dev.group2.traveldiary.travel_diary_backend.model.Activity;
import dev.group2.traveldiary.travel_diary_backend.dto.ActivityDTO;
import dev.group2.traveldiary.travel_diary_backend.service.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<ActivityDTO> addActivity(@RequestBody Activity activity) {
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
    public ResponseEntity<ActivityDTO> modifyActivity(@PathVariable Long activityId,
                                                      @RequestParam(required = false) String title,
                                                      @RequestParam(required = false) Boolean completionStatus) {
       return ResponseEntity.status(HttpStatus.OK).body(activityService.updateActivity(activityId,title,completionStatus));
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<Map<String,String>> deleteActivity(@PathVariable Long activityId) {
        activityService.deleteActivity(activityId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Activity Deleted Successfully"));
    }

}
