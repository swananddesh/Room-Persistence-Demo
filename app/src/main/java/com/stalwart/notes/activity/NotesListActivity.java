package com.stalwart.notes.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.stalwart.notes.R;
import com.stalwart.notes.adapters.NotesRecyclerAdapter;
import com.stalwart.notes.models.Note;
import com.stalwart.notes.utils.VerticalSpacingItemDecorator;

import java.util.ArrayList;

public class NotesListActivity extends AppCompatActivity implements NotesRecyclerAdapter.OnNoteListener {

    private static final String TAG = "NotesListActivity";

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

        Toolbar notesToolbar = findViewById(R.id.notes_toolbar);
        setSupportActionBar(notesToolbar);
        setTitle(getString(R.string.app_name));
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        notesRecyclerView.setLayoutManager(layoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(20);
        notesRecyclerView.addItemDecoration(itemDecorator);
        notesAdapter = new NotesRecyclerAdapter(notes, this);
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

    @Override
    public void onNoteClick(int position) {
        Log.d(TAG, "Note #" + position);
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("selected_note", notes.get(position));
        startActivity(intent);
    }
}
