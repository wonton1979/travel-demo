package dev.group2.traveldiary.travel_diary_backend.controller;

import dev.group2.traveldiary.travel_diary_backend.model.Note;
import dev.group2.traveldiary.travel_diary_backend.dto.NoteDTO;
import dev.group2.traveldiary.travel_diary_backend.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService noteService;
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    @PostMapping
    public ResponseEntity<NoteDTO> addCountry(@RequestBody Note note) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.createNote(note));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<NoteDTO>> getNoteByActivityId(@PathVariable Long activityId) {
        List<NoteDTO> notes = noteService.getNotesByActivityId(activityId);
        return ResponseEntity.status(HttpStatus.OK).body(notes);
    }

    @PatchMapping("/{noteId}")
    public ResponseEntity<NoteDTO> modifyNote(@PathVariable Long noteId, @RequestParam String text) {
       return ResponseEntity.status(HttpStatus.OK).body(noteService.updateNote(noteId,text));
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Map<String,String>> deleteNote(@PathVariable Long noteId) {
        noteService.deleteNote(noteId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Note Deleted Successfully"));
    }
}
