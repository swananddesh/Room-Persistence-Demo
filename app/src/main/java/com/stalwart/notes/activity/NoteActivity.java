package com.stalwart.notes.activity;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.stalwart.notes.R;
import com.stalwart.notes.customview.LinedEditText;
import com.stalwart.notes.models.Note;
import com.stalwart.notes.persistence.NoteRepository;
import com.stalwart.notes.utils.DateUtil;

import java.util.Objects;

public class NoteActivity extends AppCompatActivity implements View.OnTouchListener,
        GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, TextWatcher {

    private static final String TAG = "NoteActivity";
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;


    // UI components
    private LinedEditText notesContentEditText;
    private TextView noteTitle;
    private EditText noteTitleEditText;
    private RelativeLayout backArrowContainer;
    private RelativeLayout checkContainer;
    private ImageButton imgBackArrow;
    private ImageButton imgCheck;

    // vars
    private boolean isNewNote;
    private Note initialNote;
    private GestureDetector gestureDetector;
    private int mode;
    private NoteRepository noteRepository;
    private Note finalNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        initView();

        if (getIntentData()) {
            // This is new note i.e. EDIT MODE
            setNewNoteProperties();
            enableEditMode();
        } else {
            // This is NOT new note i.e. VIEW MODE
            setNoteProperties();
            disableContentInteraction();
        }

        setListeners();
        noteRepository = new NoteRepository(this);
    }

    private void initView() {
        notesContentEditText = findViewById(R.id.note_text);
        noteTitle = findViewById(R.id.toolbar_note_title);
        noteTitleEditText = findViewById(R.id.toolbar_note_edit_title);
        backArrowContainer = findViewById(R.id.back_arrow_container);
        checkContainer = findViewById(R.id.check_mark_container);
        imgBackArrow = findViewById(R.id.toolbar_back_arrow);
        imgCheck = findViewById(R.id.toolbar_check_mark);
    }

    private boolean getIntentData() {
        if (getIntent().hasExtra("selected_note")) {

            initialNote = getIntent().getParcelableExtra("selected_note");

            finalNote = new Note();
            finalNote.setId(initialNote.getId());
            finalNote.setTitle(initialNote.getTitle());
            finalNote.setContent(initialNote.getContent());
            finalNote.setTimestamp(initialNote.getTimestamp());

            mode = EDIT_MODE_DISABLED;
            isNewNote = false;
            return false;
        }

        mode = EDIT_MODE_ENABLED;
        isNewNote = true;
        return true;
    }

    private void saveChanges() {
        if (isNewNote) {
            saveNewNote();
        } else {
            updateNote();
        }
    }

    private void updateNote() {
        noteRepository.updateNoteTask(finalNote);
    }

    private void saveNewNote() {
        noteRepository.insertNoteTask(finalNote);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListeners() {
        notesContentEditText.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this, this);
        gestureDetector.setOnDoubleTapListener(this);
        imgCheck.setOnClickListener(new DisableEditModeOnCheckClickListener());
        noteTitle.setOnClickListener(new EnableEditModeOnTitleClickListener());
        imgBackArrow.setOnClickListener(new CloseNoteClickListener());
        noteTitleEditText.addTextChangedListener(this);
    }

    private void disableContentInteraction() {
        notesContentEditText.setKeyListener(null);
        notesContentEditText.setFocusable(false);
        notesContentEditText.setFocusableInTouchMode(false);
        notesContentEditText.setCursorVisible(false);
        notesContentEditText.clearFocus();
    }

    private void enableContentInteraction() {
        notesContentEditText.setKeyListener(new EditText(this).getKeyListener());
        notesContentEditText.setFocusable(true);
        notesContentEditText.setFocusableInTouchMode(true);
        notesContentEditText.setCursorVisible(true);
        notesContentEditText.requestFocus();
    }

    private void enableEditMode() {
        noteTitle.setVisibility(View.GONE);
        noteTitleEditText.setVisibility(View.VISIBLE);

        backArrowContainer.setVisibility(View.GONE);
        checkContainer.setVisibility(View.VISIBLE);

        mode = EDIT_MODE_ENABLED;

        enableContentInteraction();
    }

    private void disableEditMode() {
        noteTitle.setVisibility(View.VISIBLE);
        noteTitleEditText.setVisibility(View.GONE);

        backArrowContainer.setVisibility(View.VISIBLE);
        checkContainer.setVisibility(View.GONE);

        mode = EDIT_MODE_DISABLED;

        disableContentInteraction();

        String noteContent = Objects.requireNonNull(notesContentEditText.getText()).toString();
        noteContent = noteContent.replace("\n", "");
        noteContent = noteContent.replace(" ", "");
        if (noteContent.length() > 0) {
            finalNote.setTitle(noteTitleEditText.getText().toString());
            finalNote.setContent(notesContentEditText.getText().toString());
            finalNote.setTimestamp(DateUtil.getCurrentTimeStamp());

            if (!finalNote.getContent().equals(initialNote.getContent())
                    || !finalNote.getTitle().equals(initialNote.getTitle())) {
                saveChanges();
            }
        }
    }

    private void setNoteProperties() {
        noteTitle.setText(initialNote.getTitle());
        noteTitleEditText.setText(initialNote.getTitle());
        notesContentEditText.setText(initialNote.getContent());
    }

    private void setNewNoteProperties() {
        noteTitle.setText(getString(R.string.new_note_title));
        noteTitleEditText.setText(getString(R.string.new_note_title));

        initialNote = new Note();
        finalNote = new Note();
        initialNote.setTitle(getString(R.string.new_note_title));
        finalNote.setTitle(getString(R.string.new_note_title));
    }


    @SuppressLint("ClickableViewAccessibility")
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
        enableEditMode();
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        noteTitle.setText(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private class DisableEditModeOnCheckClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            hideSoftKeyboard();
            disableEditMode();
        }
    }

    private class EnableEditModeOnTitleClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            enableEditMode();
            noteTitleEditText.requestFocus();
            noteTitleEditText.setSelection(noteTitleEditText.length());
        }
    }

    private class CloseNoteClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (mode == EDIT_MODE_ENABLED) {
            disableEditMode();
        } else {
            super.onBackPressed();
        }
    }

    private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode", mode);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mode = savedInstanceState.getInt("mode");
        if (mode == EDIT_MODE_ENABLED) {
            enableEditMode();
        }
    }
}
