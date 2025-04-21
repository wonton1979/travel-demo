package dev.group2.traveldiary.travel_diary_backend.controller;
import dev.group2.traveldiary.travel_diary_backend.model.Activity;
import dev.group2.traveldiary.travel_diary_backend.model.Note;
import dev.group2.traveldiary.travel_diary_backend.model.Photo;
import dev.group2.traveldiary.travel_diary_backend.dto.PhotoDTO;
import dev.group2.traveldiary.travel_diary_backend.model.User;
import dev.group2.traveldiary.travel_diary_backend.repository.PhotoRepository;
import dev.group2.traveldiary.travel_diary_backend.repository.UserRepository;
import dev.group2.traveldiary.travel_diary_backend.service.ActivityService;
import dev.group2.traveldiary.travel_diary_backend.service.ItineraryService;
import dev.group2.traveldiary.travel_diary_backend.service.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {
    private final PhotoService photoService;
    private final ActivityService activityService;
    private final UserRepository userRepository;
    private final ItineraryService itineraryService;
    private final PhotoRepository photoRepository;
    public PhotoController(PhotoService photoService,
                           ActivityService activityService,
                           UserRepository userRepository,
                           ItineraryService itineraryService,
                           PhotoRepository photoRepository) {
        this.photoService = photoService;
        this.activityService = activityService;
        this.userRepository = userRepository;
        this.itineraryService = itineraryService;
        this.photoRepository = photoRepository;
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<Object> fetchPhotoById(@PathVariable Long photoId,@AuthenticationPrincipal UserDetails userDetails) {
        PhotoDTO photo = photoService.getPhotoById(photoId);
        return ResponseEntity.status(HttpStatus.OK).body(photo);
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<PhotoDTO>> fetchPhotoByActivityId(@PathVariable Long activityId) {
        List<PhotoDTO> photoDTOS = photoService.getPhotoByActivityId(activityId);
        return ResponseEntity.status(HttpStatus.OK).body(photoDTOS);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createPhoto(@RequestParam("file") MultipartFile file,
                                             @RequestParam("caption") String caption,
                                             @RequestParam("activityId") Long activityId,
                                             @RequestParam("modifiedAt") Instant modifiedAt,
                                              @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Activity activity = activityService.getActivityEntityById(activityId);
        Long itineraryUserId = itineraryService.getItineraryById(activity.getItinerary().getItineraryId()).getUserId();
        if(!itineraryUserId.equals(user.getUserId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        Photo photo = new Photo();
        photo.setCaption(caption);
        photo.setActivity(activity);
        photo.setModifiedAt(modifiedAt);
        return ResponseEntity.status(HttpStatus.CREATED).body(photoService.createPhoto(photo,file));
    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity<Map<String,String>> deletePhoto(@PathVariable Long photoId,@AuthenticationPrincipal UserDetails userDetails) throws IOException {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Photo currentPhoto = photoRepository.getPhotoByPhotoId(photoId);
        Activity activity = activityService.getActivityEntityById(currentPhoto.getActivity().getActivityId());
        Long itineraryUserId = itineraryService.getItineraryById(activity.getItinerary().getItineraryId()).getUserId();
        if(!itineraryUserId.equals(user.getUserId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        photoService.deletePhoto(photoId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Photo Deleted Successfully"));
    }

    @PatchMapping("/{photoId}")
    public ResponseEntity<Object> modifyPhoto(@PathVariable Long photoId,
                                              @RequestParam MultipartFile file,
                                              @RequestParam("caption") String caption,
                                              @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Photo currentPhoto = photoRepository.getPhotoByPhotoId(photoId);
        Activity activity = activityService.getActivityEntityById(currentPhoto.getActivity().getActivityId());
        Long itineraryUserId = itineraryService.getItineraryById(activity.getItinerary().getItineraryId()).getUserId();
        if(!itineraryUserId.equals(user.getUserId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(photoService.updatePhoto(photoId,file,caption));

    }
}
