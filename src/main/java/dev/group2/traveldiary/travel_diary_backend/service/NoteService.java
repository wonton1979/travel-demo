package dev.group2.traveldiary.travel_diary_backend.service;



import dev.group2.traveldiary.travel_diary_backend.model.Note;
import dev.group2.traveldiary.travel_diary_backend.dto.NoteDTO;
import dev.group2.traveldiary.travel_diary_backend.exception.ContentNotFoundException;
import dev.group2.traveldiary.travel_diary_backend.repository.ActivityRepository;
import dev.group2.traveldiary.travel_diary_backend.repository.NoteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final ActivityRepository activityRepository;

    public NoteService(NoteRepository noteRepository, ActivityRepository activityRepository) {
        this.noteRepository = noteRepository;
        this.activityRepository = activityRepository;
    }

    public NoteDTO createNote(Note note) {
        return new NoteDTO(noteRepository.save(note));
    }

    public List<NoteDTO> getNotesByActivityId(Long activityId) {
        if(activityRepository.existsById(activityId)){
            List<Note> notes = noteRepository.findAllByActivity_ActivityId(activityId);
            List<NoteDTO> noteDTOS = new java.util.ArrayList<>();
            for(Note note : notes){
                noteDTOS.add(new NoteDTO(note));
            }
            return noteDTOS;
        }
        else{
            throw new ContentNotFoundException("Activity with id " + activityId + " not found");
        }
    }

    public NoteDTO updateNote(Long noteId, String text){
        Optional<Note> note = noteRepository.findById(noteId);
        if(note.isPresent()){
            Note existingNote = note.get();
            if(text != null){
                existingNote.setText(text);
                return new NoteDTO(noteRepository.save(existingNote));
            }
            else{
                throw new ContentNotFoundException("text is null");
            }

        }
        else{
            throw new ContentNotFoundException("Note with id " + noteId + " not found");
        }
    }

    public void deleteNote(Long noteId) {
        if(noteRepository.existsById(noteId)){
            noteRepository.deleteById(noteId);
        }
        else{
            throw new ContentNotFoundException("Note with id " + noteId + " not found");
        }
    }
}
