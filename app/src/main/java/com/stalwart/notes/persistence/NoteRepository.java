package com.stalwart.notes.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.stalwart.notes.models.Note;

import java.util.List;

public class NoteRepository {

    private NoteDatabase noteDatabase;

    public NoteRepository(Context context) {
        noteDatabase = NoteDatabase.getInstance(context);
    }

    public void insertNoteTask(Note note) {

    }

    public void updateNoteTask(Note note) {

    }

    public LiveData<List<Note>> retrieveNotes() {
        return noteDatabase.getNoteDAO().getNotes();
    }

    public void deleteNoteTask(Note note) {

    }

}
