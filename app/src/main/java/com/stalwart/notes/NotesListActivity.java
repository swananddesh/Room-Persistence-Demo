package com.stalwart.notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.stalwart.notes.adapters.NotesRecyclerAdapter;
import com.stalwart.notes.models.Note;
import com.stalwart.notes.utils.VerticalSpacingItemDecorator;

import java.util.ArrayList;

public class NotesListActivity extends AppCompatActivity {


    // UI Components
    private RecyclerView notesRecyclerView;

    // vars
    private ArrayList<Note> notes = new ArrayList<>();
    private NotesRecyclerAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        notesRecyclerView = findViewById(R.id.noteRecyclerView);

        initRecyclerView();
        insertFakeNotes();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        notesRecyclerView.setLayoutManager(layoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(20);
        notesRecyclerView.addItemDecoration(itemDecorator);
        notesAdapter = new NotesRecyclerAdapter(notes);
        notesRecyclerView.setAdapter(notesAdapter);
    }

    private void insertFakeNotes() {
        for (int index = 0; index < 20; index++) {
            Note note = new Note();
            note.setTitle("Title #" + index);
            note.setTimestamp("Jul 2019");
            notes.add(note);
        }
        notesAdapter.notifyDataSetChanged();
    }
}
