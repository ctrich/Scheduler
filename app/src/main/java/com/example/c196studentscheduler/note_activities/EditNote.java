package com.example.c196studentscheduler.note_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.entity.Note;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.NoteViewModel;
import com.example.c196studentscheduler.viewmodel.NoteViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
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
    private boolean editing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        setTitle(Constants.NOTES_EDIT_TITLE);
        //display a back arrow in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            editing = savedInstanceState.getBoolean(Constants.EDITING_KEY);
        }
        initViewModel();
    }

    /**
     *
     * @param outState
     * Handle configuration changes
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(Constants.EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }

    /**
     *
     * @param item
     * Pass the course id when the actionbar back button is pressed
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent(this, NoteList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
        return true;
    }

    /**
     * initialize the view model and set the text view text
     */
    private void initViewModel() {
        factory = new NoteViewModelFactory(this.getApplication());
        noteViewModel = ViewModelProviders.of(this, factory).get(NoteViewModel.class);

        noteViewModel.mLiveNote.observe(this, note -> {
            if (note != null) {
                noteToDelete = note;
                courseId = note.getCourseId();
                //Don't update the edit text when the configuration changes
                if(!editing) {
                    noteText.setText(note.getNoteText());
                }
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

    /**
     *
     * @param view
     * Update and existing note in the database
     */
    public void updateNote(View view) {
        if (!noteText.getText().toString().isEmpty()) {
            currentNote = new Note(noteId, noteText.getText().toString(), courseId);
            noteViewModel.updateNote(currentNote);

            Intent intent = new Intent(this, NoteList.class);
            intent.putExtra(Constants.COURSE_ID_KEY, courseId);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please enter note text", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Delete the current note
     * @param view
     */
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
