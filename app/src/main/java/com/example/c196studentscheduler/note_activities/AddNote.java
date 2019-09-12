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
public class AddNote extends AppCompatActivity {
    @BindView(R.id.note_add_text)
    EditText noteText;
    @BindView(R.id.note_add_save_fab)
    FloatingActionButton saveFab;

    private NoteViewModelFactory factory;
    private NoteViewModel noteViewModel;
    private int courseId;
    private Note currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        setTitle(Constants.NOTES_ADD_TITLE);
        //display a back arrow in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        initViewModel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent(this, NoteList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
        return true;
    }

    /**
     * initialize the view model
     */
    private void initViewModel() {
        factory = new NoteViewModelFactory(this.getApplication());
        noteViewModel = ViewModelProviders.of(this, factory)
                .get(NoteViewModel.class);
        Bundle extras = getIntent().getExtras();
        courseId = extras.getInt(Constants.COURSE_ID_KEY);
    }

    /**
     * Load the Note List when the devices back button is pressed
     */
    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, NoteList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    /**
     *
     * @param view
     * Insert a new note in the database
     */
    public void saveNote(View view) {
        if (!noteText.getText().toString().isEmpty()) {
            currentNote = new Note(noteText.getText().toString(), courseId);
            noteViewModel.addNote(currentNote);

            Intent intent = new Intent(this, NoteList.class);
            intent.putExtra(Constants.COURSE_ID_KEY, courseId);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please enter note text", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelNoteAdd(View view) {
        Intent intent = new Intent(this, NoteList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }


}
