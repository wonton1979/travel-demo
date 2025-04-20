package dev.group2.traveldiary.travel_diary_backend.repository;
import dev.group2.traveldiary.travel_diary_backend.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByActivity_ActivityId(Long activityId);

    Note getNoteByNoteId(Long noteId);
}
