package com.example.c196studentscheduler.course_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.c196studentscheduler.R;

import com.example.c196studentscheduler.entity.Course;
import com.example.c196studentscheduler.entity.Term;
import com.example.c196studentscheduler.mentor_activities.AddMentor;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.CourseViewModel;
import com.example.c196studentscheduler.viewmodel.CourseViewModelFactory;
import com.example.c196studentscheduler.viewmodel.TermViewModel;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCourse extends AppCompatActivity {

    @BindView(R.id.add_course_title)
    EditText courseTitle;

    @BindView(R.id.add_course_sdate)
    EditText sDate;
    @BindView(R.id.add_course_edate)
    EditText eDate;
    @BindView(R.id.add_course_status)
    EditText courseSatus;
    @BindView(R.id.add_course_save)
    Button saveBtn;


    private Course currentCourse;
    CourseViewModel courseViewModel;
    TermViewModel termViewModel;
    private int termId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        setTitle(Constants.COURSE_ADD_TITLE);
        ButterKnife.bind(this);
        initViewModel();
    }

    private void initViewModel() {
        CourseViewModelFactory factory = new CourseViewModelFactory(this.getApplication());
        courseViewModel = ViewModelProviders.of(this, factory)
                .get(CourseViewModel.class);
        termViewModel = ViewModelProviders.of(this)
                .get(TermViewModel.class);
        Bundle extras = getIntent().getExtras();
        termId = extras.getInt(Constants.TERM_ID_KEY);
    }

    public void saveCourse(View view) throws ParseException {
        String sd = sDate.getText().toString();
        String ed = eDate.getText().toString();
        String courseName = courseTitle.getText().toString();
        String status = courseSatus.getText().toString();


        Date startD = convertStringToDate(sd);
        Date endD = convertStringToDate(ed);
        currentCourse = new Course(courseName, termId,status, startD, endD);
        courseViewModel.addCourse(currentCourse);

        Intent intent = new Intent(this, CourseList.class);
        intent.putExtra(Constants.TERM_ID_KEY, termId);
        startActivity(intent);
    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, CourseList.class);
        intent.putExtra(Constants.TERM_ID_KEY, termId);
        startActivity(intent);
    }


    public void cancelAdd(View view) {
        Intent intent = new Intent(this, CourseList.class);
        intent.putExtra(Constants.TERM_ID_KEY, termId);
        startActivity(intent);
    }

    public Date convertStringToDate(String sDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = simpleDateFormat.parse(sDate);
        return date;
    }

}
