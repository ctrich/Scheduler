package com.example.c196studentscheduler.course_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.TermDetails;
import com.example.c196studentscheduler.adapter.CourseListAdapter;
import com.example.c196studentscheduler.entity.Course;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.CourseViewModel;
import com.example.c196studentscheduler.viewmodel.CourseViewModelFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseList extends AppCompatActivity {
    private static final String TAG = "CourseList";


    @BindView(R.id.list_course_recycler)
    RecyclerView recyclerView;
    private List<Course> courseData = new ArrayList<>();
    private CourseListAdapter mAdapter;
    private CourseViewModel courseViewModel;
    private int termId;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        setTitle(Constants.COURSE_LIST_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        termId = extras.getInt(Constants.TERM_ID_KEY);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent(this, TermDetails.class);
        intent.putExtra(Constants.TERM_ID_KEY, termId);
        startActivity(intent);
        return true;
    }

    private void initViewModel() {
        final Observer<List<Course>> courseObserver = new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                courseData.clear();
                courseData.addAll(courses);
                if (mAdapter == null) {
                    mAdapter = new CourseListAdapter(courseData, CourseList.this);
                    recyclerView.setAdapter(mAdapter);
                }else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        };
        CourseViewModelFactory factory = new CourseViewModelFactory(this.getApplication(), termId);
        courseViewModel = ViewModelProviders.of(this, factory).get(CourseViewModel.class);
        courseViewModel.mCourseByTerm.observe(this, courseObserver);
    }

    public void showAddCourse(View view) {
        Intent intent = new Intent(this, AddCourse.class);
        intent.putExtra(Constants.TERM_ID_KEY, termId);
        startActivity(intent);
    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, TermDetails.class);
        intent.putExtra(Constants.TERM_ID_KEY, termId);
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
