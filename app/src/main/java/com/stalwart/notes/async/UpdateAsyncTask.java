package com.stalwart.notes.async;

import android.os.AsyncTask;

import com.stalwart.notes.models.Note;
import com.stalwart.notes.persistence.NoteDAO;

public class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDAO noteDAO;

    public UpdateAsyncTask(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDAO.update(notes);
        return null;
    }
}
