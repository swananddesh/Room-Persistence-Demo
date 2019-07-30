package com.stalwart.notes.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.stalwart.notes.R;
import com.stalwart.notes.customview.LinedEditText;
import com.stalwart.notes.models.Note;

public class NoteActivity extends AppCompatActivity implements View.OnTouchListener,
        GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private static final String TAG = "NoteActivity";

    // UI components
    private LinedEditText notesContentEditText;
    private TextView noteTitle;
    private EditText noteTitleEditText;

    // vars
    private boolean isNewNote;
    private Note initialNote;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        notesContentEditText = findViewById(R.id.note_text);
        noteTitle = findViewById(R.id.toolbar_note_title);
        noteTitleEditText = findViewById(R.id.toolbar_note_edit_title);

        if (getIntentData()) {
            // This is new note i.e. EDIT MODE
            setNewNoteProperties();
        } else {
            // This is NOT new note i.e. VIEW MODE
            setNoteProperties();
        }

        setListeners();
    }

    private boolean getIntentData() {
        if (getIntent().hasExtra("selected_note")) {

            initialNote = getIntent().getParcelableExtra("selected_note");

            isNewNote = false;
            return false;
        }

        isNewNote = true;
        return true;
    }

    private void setNoteProperties() {
        noteTitle.setText(initialNote.getTitle());
        noteTitleEditText.setText(initialNote.getTitle());
        notesContentEditText.setText(initialNote.getContent());
    }

    private void setNewNoteProperties() {
        noteTitle.setText("Note Title");
        noteTitleEditText.setText("Note Title");
    }

    private void setListeners() {
        notesContentEditText.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this, this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: double tapped!!!");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
