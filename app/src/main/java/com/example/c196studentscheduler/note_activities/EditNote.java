package com.example.c196studentscheduler.note_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.entity.Note;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.NoteViewModel;
import com.example.c196studentscheduler.viewmodel.NoteViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditNote extends AppCompatActivity {
    @BindView(R.id.note_edit_text)
    EditText noteText;
    @BindView(R.id.note_edit_delete_fab)
    FloatingActionButton deleteNote;
    @BindView(R.id.note_edit_save_fab)
    FloatingActionButton saveNote;

    private NoteViewModelFactory factory;
    private NoteViewModel noteViewModel;
    private int courseId;
    private int noteId;
    private Note noteToDelete;
    private Note currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        setTitle(Constants.NOTES_EDIT_TITLE);

        ButterKnife.bind(this);
        initViewModel();
    }

    private void initViewModel() {
        factory = new NoteViewModelFactory(this.getApplication());
        noteViewModel = ViewModelProviders.of(this, factory).get(NoteViewModel.class);

        noteViewModel.mLiveNote.observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                noteToDelete = note;

                courseId = note.getCourseId();
                noteText.setText(note.getNoteText());

            }
        });

        Bundle extras = getIntent().getExtras();
        noteId = extras.getInt(Constants.NOTE_ID_KEY);
        noteViewModel.loadData(noteId);

    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, NoteList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }


    public void updateNote(View view) {

        currentNote = new Note(noteId, noteText.getText().toString(), courseId);
        noteViewModel.updateNote(currentNote);

        Intent intent = new Intent(this, NoteList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    public void deleteNote(View view) {
        noteViewModel.deleteNote(noteToDelete);

        Intent intent = new Intent(this, NoteList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    public void cancelNoteEdit(View view) {
        Intent intent = new Intent(this, NoteList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

}
