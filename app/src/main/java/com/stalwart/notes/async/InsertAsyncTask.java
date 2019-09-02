package com.stalwart.notes.async;

import android.os.AsyncTask;

import com.stalwart.notes.models.Note;
import com.stalwart.notes.persistence.NoteDAO;

public class InsertAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDAO noteDAO;

    public InsertAsyncTask(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDAO.insertNotes(notes);
        return null;
    }
}
