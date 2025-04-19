package dev.group2.traveldiary.travel_diary_backend.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;

    @ManyToOne
    @JoinColumn(name="activity_id")
    private Activity activity;


    @NotBlank
    private String text;

    public Note() {}
    public Note(Activity activity, String text) {
        this.activity = activity;
        this.text = text;
    }

    public Long getNoteId() {
        return noteId;
    }
    public Activity getActivity() {
        return activity;
    }
    public String getText() {
        return text;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    public void setText(String content) {
        this.text = content;
    }
}
