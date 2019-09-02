package com.stalwart.notes.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.stalwart.notes.async.DeleteAsyncTask;
import com.stalwart.notes.async.InsertAsyncTask;
import com.stalwart.notes.async.UpdateAsyncTask;
import com.stalwart.notes.models.Note;

import java.util.List;

public class NoteRepository {

    private NoteDatabase noteDatabase;

    public NoteRepository(Context context) {
        noteDatabase = NoteDatabase.getInstance(context);
    }

    public void insertNoteTask(Note note) {
        new InsertAsyncTask(noteDatabase.getNoteDAO()).execute(note);
    }

    public void updateNoteTask(Note note) {
        new UpdateAsyncTask(noteDatabase.getNoteDAO()).execute(note);
    }

    public LiveData<List<Note>> retrieveNotes() {
        return noteDatabase.getNoteDAO().getNotes();
    }

    public void deleteNoteTask(Note note) {
        new DeleteAsyncTask(noteDatabase.getNoteDAO()).execute(note);
    }

}
