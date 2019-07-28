package com.stalwart.notes.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stalwart.notes.R;
import com.stalwart.notes.models.Note;

import java.util.ArrayList;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.NoteHolder> {

    private ArrayList<Note> notes = new ArrayList<>();

    public NotesRecyclerAdapter(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NoteHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_note_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int position) {
        if (notes != null && !notes.isEmpty()) {
            noteHolder.noteTitle.setText(notes.get(position).getTitle());
            noteHolder.noteTimestamp.setText(notes.get(position).getTimestamp());
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NoteHolder extends RecyclerView.ViewHolder {

        TextView noteTitle;
        TextView noteTimestamp;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.note_title);
            noteTimestamp = itemView.findViewById(R.id.note_timestamp);
        }
    }
}
