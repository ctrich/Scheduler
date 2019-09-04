package com.example.c196studentscheduler.course_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.adapter.CourseListAdapter;
import com.example.c196studentscheduler.entity.Course;
import com.example.c196studentscheduler.viewmodel.CourseViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseList extends AppCompatActivity {

    @BindView(R.id.list_course_recycler)
    RecyclerView recyclerView;
    private List<Course> courseData = new ArrayList<>();
    private CourseListAdapter mAdapter;
    private CourseViewModel courseViewModel;
    String name = "name";
    int termId = 1;
    String status = "complete";
    Date date = new Date();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        setTitle("Course List");


        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {

        final Observer<List<Course>> termObserver = terms -> {
            courseData.clear();
            courseData.addAll(terms);
            if (mAdapter == null) {
                mAdapter = new CourseListAdapter(courseData, CourseList.this);
                recyclerView.setAdapter(mAdapter);
            }else {
                mAdapter.notifyDataSetChanged();
            }

        };

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.mCourses.observe(this, termObserver);
    }

    public void showAddCourse(View view) {
        Intent intent = new Intent(this, AddCourse.class);
        startActivity(intent);
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}
