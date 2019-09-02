package com.stalwart.notes.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stalwart.notes.R;
import com.stalwart.notes.models.Note;
import com.stalwart.notes.utils.DateUtil;

import java.util.ArrayList;


public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.NoteHolder> {

    private ArrayList<Note> notes;
    private OnNoteListener onNoteListener;
    private static final String TAG = "NotesRecyclerAdapter";

    public NotesRecyclerAdapter(ArrayList<Note> notes, OnNoteListener onNoteListener) {
        this.notes = notes;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NoteHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_note_row, viewGroup, false), onNoteListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int position) {
        if (notes != null && !notes.isEmpty()) {
            try {
                String month = notes.get(position).getTimestamp().substring(0, 2);
                month = DateUtil.getMonthFromNumber(month);
                String year = notes.get(position).getTimestamp().substring(3);

                noteHolder.noteTitle.setText(notes.get(position).getTitle());
                noteHolder.noteTimestamp.setText(month + " " + year);

            }catch (NullPointerException ex) {
                Log.d(TAG, "onBindViewHolder: Null Pointer Exception" + ex.getMessage());
            }

        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView noteTitle;
        TextView noteTimestamp;
        OnNoteListener onNoteListener;

        NoteHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.note_title);
            noteTimestamp = itemView.findViewById(R.id.note_timestamp);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}
