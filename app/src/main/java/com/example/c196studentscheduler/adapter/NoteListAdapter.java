package com.example.c196studentscheduler.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.entity.Note;
import com.example.c196studentscheduler.note_activities.EditNote;
import com.example.c196studentscheduler.util.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 *
 * Responsible for displaying all mentors for the selected course in a recyclerview
 */
public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private final Context context;
    private final List<Note> notes;

    public NoteListAdapter(List<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.noteText.setText(notes.get(position).getNoteText());
        //Pass the select notes noteID to the edit note activity
        holder.editFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditNote.class);
                intent.putExtra(Constants.NOTE_ID_KEY, notes.get(position).getNoteId());
                context.startActivity(intent);
            }
        });
        //Opens the email client and places the note text in the email body
        holder.emailFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject= "Notes";
                String body= notes.get(position).getNoteText();
                String mailTo = "mailto:" +
                        "?&subject=" + Uri.encode(subject) +
                        "&body=" + Uri.encode(body);
                Intent emailIntent = new Intent(Intent.ACTION_VIEW);
                emailIntent.setData(Uri.parse(mailTo));
                context.startActivity(emailIntent);
            }
        });
    }

    /**
     *
     * @return the number of notes for the selected course
     */
    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.note_list_item)
        TextView noteText;
        @BindView(R.id.note_list_edit_fab)
        FloatingActionButton editFab;
        @BindView(R.id.note_list_email_fab)
        FloatingActionButton emailFab;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }
}
