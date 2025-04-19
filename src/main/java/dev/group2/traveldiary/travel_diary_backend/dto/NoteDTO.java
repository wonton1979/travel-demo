package dev.group2.traveldiary.travel_diary_backend.dto;

import dev.group2.traveldiary.travel_diary_backend.model.Note;

public class NoteDTO {
    private final Long NoteId;
    private final Long activityId;
    private final String text;
    public NoteDTO( Note note) {
        this.NoteId = note.getNoteId();
        this.activityId = note.getActivity().getActivityId();
        this.text = note.getText();
    }
    public Long getNoteId() {
        return NoteId;
    }
    public Long getActivityId() {
        return activityId;
    }
    public String getText() {
        return text;
    }
}
