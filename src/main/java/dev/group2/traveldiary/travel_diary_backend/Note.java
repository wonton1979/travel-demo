package dev.group2.traveldiary.travel_diary_backend;
import jakarta.persistence.*;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;

    @ManyToOne
    @JoinColumn(name="activity_id")
    private Activity activity;

    private String content;

    public Note() {}
    public Note(Activity activity, String content) {
        this.activity = activity;
        this.content = content;
    }

    public Long getNoteId() {
        return noteId;
    }
    public Activity getActivity() {
        return activity;
    }
    public String getContent() {
        return content;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
