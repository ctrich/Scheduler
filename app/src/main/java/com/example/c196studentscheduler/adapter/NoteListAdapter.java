package com.example.c196studentscheduler.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.entity.Note;
import com.example.c196studentscheduler.note_activities.EditNote;
import com.example.c196studentscheduler.note_activities.NoteList;
import com.example.c196studentscheduler.util.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        holder.editFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditNote.class);
                intent.putExtra(Constants.NOTE_ID_KEY, notes.get(position).getNoteId());
                context.startActivity(intent);
            }
        });

        holder.emailFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
//                intent.putExtra(Intent.EXTRA_SUBJECT, "Notes");
//                intent.putExtra(Intent.EXTRA_TEXT, holder.noteText.getText().toString());
//                context.startActivity(Intent.createChooser(intent, "Send Email"));


//                String to = "test@gmail.com";
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
