package com.stalwart.notes.async;

import android.os.AsyncTask;

import com.stalwart.notes.models.Note;
import com.stalwart.notes.persistence.NoteDAO;

public class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDAO noteDAO;

    public DeleteAsyncTask(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDAO.delete(notes);
        return null;
    }
}
