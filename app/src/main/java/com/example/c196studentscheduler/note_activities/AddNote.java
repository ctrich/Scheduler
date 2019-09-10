package com.example.c196studentscheduler.note_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

    private void initViewModel() {
        factory = new NoteViewModelFactory(this.getApplication());
        noteViewModel = ViewModelProviders.of(this, factory)
                .get(NoteViewModel.class);
        Bundle extras = getIntent().getExtras();
        courseId = extras.getInt(Constants.COURSE_ID_KEY);
    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, NoteList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    public void saveNote(View view) {

        currentNote = new Note(noteText.getText().toString(), courseId);
        noteViewModel.addNote(currentNote);

        Intent intent = new Intent(this, NoteList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    public void cancelNoteAdd(View view) {
        Intent intent = new Intent(this, NoteList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }


}
