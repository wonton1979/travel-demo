package dev.group2.traveldiary.travel_diary_backend.controller;

import dev.group2.traveldiary.travel_diary_backend.model.Activity;
import dev.group2.traveldiary.travel_diary_backend.model.Note;
import dev.group2.traveldiary.travel_diary_backend.dto.NoteDTO;
import dev.group2.traveldiary.travel_diary_backend.model.User;
import dev.group2.traveldiary.travel_diary_backend.repository.NoteRepository;
import dev.group2.traveldiary.travel_diary_backend.service.ActivityService;
import dev.group2.traveldiary.travel_diary_backend.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import dev.group2.traveldiary.travel_diary_backend.repository.UserRepository;
import dev.group2.traveldiary.travel_diary_backend.service.ItineraryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService noteService;
    private final UserRepository userRepository;
    private final ItineraryService itineraryService;
    private final ActivityService activityService;
    private final NoteRepository noteRepository;

    public NoteController(NoteService noteService, UserRepository userRepository, ItineraryService itineraryService, ActivityService activityService, NoteRepository noteRepository) {
        this.noteService = noteService;
        this.userRepository = userRepository;
        this.itineraryService = itineraryService;
        this.activityService = activityService;
        this.noteRepository = noteRepository;
    }


    @PostMapping
    public ResponseEntity<Object> addCountry(@RequestBody Note note,@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Activity activity = activityService.getActivityEntityById(note.getActivity().getActivityId());
        Long itineraryUserId = itineraryService.getItineraryById(activity.getItinerary().getItineraryId()).getUserId();
        if(!itineraryUserId.equals(user.getUserId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.createNote(note));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<NoteDTO>> getNoteByActivityId(@PathVariable Long activityId) {
        List<NoteDTO> notes = noteService.getNotesByActivityId(activityId);
        return ResponseEntity.status(HttpStatus.OK).body(notes);
    }

    @PatchMapping("/{noteId}")
    public ResponseEntity<Object> modifyNote(@PathVariable Long noteId,
                                              @RequestBody Note note,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Note currentNote = noteRepository.getNoteByNoteId(noteId);
        Activity activity = activityService.getActivityEntityById(currentNote.getActivity().getActivityId());
        Long itineraryUserId = itineraryService.getItineraryById(activity.getItinerary().getItineraryId()).getUserId();
        if(!itineraryUserId.equals(user.getUserId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
       return ResponseEntity.status(HttpStatus.OK).body(noteService.updateNote(noteId,note.getText()));
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Map<String,String>> deleteNote(@PathVariable Long noteId,@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Note currentNote = noteRepository.getNoteByNoteId(noteId);
        Activity activity = activityService.getActivityEntityById(currentNote.getActivity().getActivityId());
        Long itineraryUserId = itineraryService.getItineraryById(activity.getItinerary().getItineraryId()).getUserId();
        if(!itineraryUserId.equals(user.getUserId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized operation."));
        }
        noteService.deleteNote(noteId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Note Deleted Successfully"));
    }
}
