package dev.group2.traveldiary.travel_diary_backend.controller;
import dev.group2.traveldiary.travel_diary_backend.model.Activity;
import dev.group2.traveldiary.travel_diary_backend.model.Photo;
import dev.group2.traveldiary.travel_diary_backend.dto.PhotoDTO;
import dev.group2.traveldiary.travel_diary_backend.service.ActivityService;
import dev.group2.traveldiary.travel_diary_backend.service.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public PhotoController(PhotoService photoService, ActivityService activityService) {
        this.photoService = photoService;
        this.activityService = activityService;
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<PhotoDTO> fetchPhotoById(@PathVariable Long photoId) {
        System.out.println(photoId+ " is the photoId");
        PhotoDTO photo = photoService.getPhotoById(photoId);
        return ResponseEntity.status(HttpStatus.OK).body(photo);
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<PhotoDTO>> fetchPhotoByActivityId(@PathVariable Long activityId) {
        List<PhotoDTO> photoDTOS = photoService.getPhotoByActivityId(activityId);
        return ResponseEntity.status(HttpStatus.OK).body(photoDTOS);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PhotoDTO> createPhoto(@RequestParam("file") MultipartFile file,
                                             @RequestParam("caption") String caption,
                                             @RequestParam("activityId") Long activityId,
                                             @RequestParam("modifiedAt") Instant modifiedAt) throws IOException {
        Photo photo = new Photo();
        Activity activity = activityService.getActivityEntityById(activityId);
        photo.setCaption(caption);
        photo.setActivity(activity);
        photo.setModifiedAt(modifiedAt);
        return ResponseEntity.status(HttpStatus.CREATED).body(photoService.createPhoto(photo,file));
    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity<Map<String,String>> deletePhoto(@PathVariable Long photoId) throws IOException {
        photoService.deletePhoto(photoId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Photo Deleted Successfully"));
    }

    @PatchMapping("/{photoId}")
    public ResponseEntity<PhotoDTO> modifyPhoto(@PathVariable Long photoId,
                                                @RequestParam MultipartFile file,
                                                @RequestParam("caption") String caption) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(photoService.updatePhoto(photoId,file,caption));

    }
}
