package com.example.c196studentscheduler.course_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196studentscheduler.R;

import com.example.c196studentscheduler.entity.Course;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.CourseViewModel;
import com.example.c196studentscheduler.viewmodel.CourseViewModelFactory;
import com.example.c196studentscheduler.viewmodel.TermViewModel;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        initViewModel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent(this, CourseList.class);
        intent.putExtra(Constants.TERM_ID_KEY, termId);
        startActivity(intent);
        return true;
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

    public void saveCourse(View view) {
        String sd = sDate.getText().toString();
        String ed = eDate.getText().toString();
        String courseName = courseTitle.getText().toString();
        String status = courseSatus.getText().toString();

        try {
            Date startD = convertStringToDate(sd);
            Date endD = convertStringToDate(ed);
            currentCourse = new Course(courseName, termId, status, startD, endD);
            courseViewModel.addCourse(currentCourse);

            Intent intent = new Intent(this, CourseList.class);
            intent.putExtra(Constants.TERM_ID_KEY, termId);
            startActivity(intent);
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid Date", Toast.LENGTH_LONG).show();
        }
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
        simpleDateFormat.setLenient(false);
        Date date = simpleDateFormat.parse(sDate);
        return date;
    }

}
