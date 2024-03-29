package com.stalwart.notes.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.stalwart.notes.models.Note;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert
    void insertNotes(Note... notes);

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();

    @Delete
    void delete(Note... notes);

    @Update
    void update(Note... notes);
}
