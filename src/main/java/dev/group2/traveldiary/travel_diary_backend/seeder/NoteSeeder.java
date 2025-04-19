package dev.group2.traveldiary.travel_diary_backend.seeder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.group2.traveldiary.travel_diary_backend.model.Note;
import dev.group2.traveldiary.travel_diary_backend.model.Activity;
import dev.group2.traveldiary.travel_diary_backend.exception.ContentNotFoundException;
import dev.group2.traveldiary.travel_diary_backend.repository.NoteRepository;
import dev.group2.traveldiary.travel_diary_backend.repository.ActivityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Component
@Order(5)
public class NoteSeeder implements CommandLineRunner {
    private final NoteRepository noteRepository;
    private final ActivityRepository activityRepository;
    private final ObjectMapper objectMapper;

    public NoteSeeder(NoteRepository noteRepository, ObjectMapper objectMapper, ActivityRepository activityRepository) {
        this.noteRepository = noteRepository;
        this.objectMapper = objectMapper;
        this.activityRepository = activityRepository;
    }

    @Override
    public void run(String... args) throws Exception{
        if (noteRepository.count() == 0) {
            InputStream inputStream = this.getClass().getResourceAsStream("/notes.json");
            if (inputStream != null) {
                List<Note> notes = objectMapper.readValue(inputStream, new TypeReference<List<Note>>() {
                });
                for(Note note : notes){
                    Long activityId = note.getActivity().getActivityId();
                    Optional<Activity> activity = activityRepository.findById(activityId);
                    if(activity.isPresent()){
                        note.setActivity(activity.get());
                    }
                    else{
                        throw new ContentNotFoundException("Activity with id " + activityId + " not found");
                    }
                }
                noteRepository.saveAll(notes);
                System.out.println("✅ Database seeded with " + notes.size() + " notes");
            }
            else {
                System.out.println("Could not find notes.json");
            }
        }
        else{
            System.out.println("Database already seeded");
        }
    }
}
