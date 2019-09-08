package com.example.c196studentscheduler.note_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.adapter.NoteListAdapter;
import com.example.c196studentscheduler.course_activities.CourseDetails;
import com.example.c196studentscheduler.entity.Note;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.NoteViewModel;
import com.example.c196studentscheduler.viewmodel.NoteViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteList extends AppCompatActivity {

    @BindView(R.id.notes_list_recycler)
    RecyclerView recyclerView;

    private NoteListAdapter mAdapter;
    private List<Note> noteData = new ArrayList<>();
    private NoteViewModelFactory factory;
    private NoteViewModel noteViewModel;
    private int courseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        setTitle(Constants.NOTES_LIST_TITLE);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {
        final Observer<List<Note>> noteObserver = new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteData.clear();
                noteData.addAll(notes);
                if (mAdapter == null) {
                    mAdapter = new NoteListAdapter(noteData, NoteList.this);
                    recyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

        Bundle extras = getIntent().getExtras();
        courseId = extras.getInt(Constants.COURSE_ID_KEY);

        factory = new NoteViewModelFactory(this.getApplication(), courseId);
        noteViewModel = ViewModelProviders.of(this, factory).get(NoteViewModel.class);
        noteViewModel.noteByCourseId.observe(this, noteObserver);
    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    public void showAddNote(View view) {
        Intent intent = new Intent(this, AddNote.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(divider);
    }
}
