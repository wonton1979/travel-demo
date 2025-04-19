package dev.group2.traveldiary.travel_diary_backend.service;
import dev.group2.traveldiary.travel_diary_backend.model.Photo;
import dev.group2.traveldiary.travel_diary_backend.dto.PhotoDTO;
import dev.group2.traveldiary.travel_diary_backend.exception.ContentNotFoundException;
import dev.group2.traveldiary.travel_diary_backend.repository.PhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.ConnectException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final CloudinaryService cloudinaryService;
    public PhotoService(PhotoRepository photoRepository, CloudinaryService cloudinaryService) {
        this.photoRepository = photoRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public PhotoDTO getPhotoById(Long photoId) {
        Optional<Photo> photo = photoRepository.findById(photoId);
        if (photo.isPresent()) {
            return new PhotoDTO(photo.get());
        }
        else {
            throw new ContentNotFoundException("Photo not found");
        }
    }

    public List<PhotoDTO> getPhotoByActivityId(Long activityId) {
        Optional<List<Photo>> photoOptional = photoRepository.findByActivity_ActivityId(activityId);
        if (photoOptional.isPresent()) {
            List<PhotoDTO> photoDTOS = new java.util.ArrayList<>();
            for(Photo photo : photoOptional.get()){
                photoDTOS.add(new PhotoDTO(photo));
            }
            return photoDTOS;
        }
        else {
            throw new RuntimeException("Activity not found");
        }
    }

    public PhotoDTO createPhoto(Photo photo, MultipartFile file) throws IOException {
        String photoUrl = cloudinaryService.uploadImage(file);
        photo.setImgUrl(photoUrl);
        PhotoDTO photoDTO = new PhotoDTO(photo);
        photoRepository.save(photo);
        return photoDTO;
    }

    public PhotoDTO updatePhoto(Long photoId , MultipartFile file, String caption) throws IOException {
        Optional<Photo> photoOptional = photoRepository.findById(photoId);
        if(photoOptional.isPresent()) {
            Photo existingPhoto = photoOptional.get();
            if(caption != null) {
                existingPhoto.setCaption(caption);
            }
            if(file != null && !file.isEmpty()) {
                cloudinaryService.deleteImage(existingPhoto.getImgUrl());
                String photoUrl = cloudinaryService.uploadImage(file);
                existingPhoto.setImgUrl(photoUrl);
            }
            existingPhoto.setModifiedAt(Instant.now());
            photoRepository.save(existingPhoto);
            return new PhotoDTO(existingPhoto);
        }
        else {
            throw new ContentNotFoundException("Photo not found");
        }
    }

    public void deletePhoto(Long photoId) throws IOException {
        Optional<Photo> photoOptional = photoRepository.findById(photoId);
        if (photoOptional.isPresent()) {
            Photo photo = photoOptional.get();
            cloudinaryService.deleteImage(photo.getImgUrl());
            photoRepository.delete(photo);
        }
        else {
            throw new ConnectException("Photo not found with id " + photoId + " in database. Could not delete photo.");
        }
    }
}
