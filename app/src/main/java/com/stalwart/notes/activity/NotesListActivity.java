package com.stalwart.notes.activity;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.util.Log;
import android.view.View;

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
        FloatingActionButton fabAddNote = findViewById(R.id.fav_add_note);

        fabAddNote.setOnClickListener(new AddNoteClickListener());
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
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(notesRecyclerView);
        notesAdapter = new NotesRecyclerAdapter(notes, this);
        notesRecyclerView.setAdapter(notesAdapter);
    }

    private void insertFakeNotes() {
        for (int index = 0; index < 20; index++) {
            Note note = new Note();
            note.setTitle("Title #" + index);
            note.setContent("Content #" + index);
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

    private void deleteNote(Note note) {
        notes.remove(note);
        notesAdapter. notifyDataSetChanged();
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            deleteNote(notes.get(viewHolder.getAdapterPosition()));
        }
    };

    private class AddNoteClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(NotesListActivity.this, NoteActivity.class);
            startActivity(intent);
        }
    }
}
