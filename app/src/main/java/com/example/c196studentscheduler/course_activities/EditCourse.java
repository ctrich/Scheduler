package com.example.c196studentscheduler.course_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditCourse extends AppCompatActivity {

    @BindView(R.id.course_edit_title)
    EditText title;
    @BindView(R.id.course_edit_status)
    EditText status;
    @BindView(R.id.course_edit_start)
    EditText courseStart;
    @BindView(R.id.course_edit_end)
    EditText courseEnd;
    @BindView(R.id.course_edit_save)
    Button saveBtn;
    @BindView(R.id.course_edit_cancel)
    Button cancelBtn;
    @BindView(R.id.course_edit_delete_fab)
    FloatingActionButton deleteFab;

    private int courseId;
    private int termId;
    private Course coursetoDelete;
    private Course currentCourse;

    CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        setTitle(Constants.COURSE_EDIT_TITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        initViewModel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
        return true;
    }

    private void initViewModel() {
        CourseViewModelFactory factory = new CourseViewModelFactory(this.getApplication());
        courseViewModel = ViewModelProviders.of(this, factory).get(CourseViewModel.class);

        courseViewModel.mLiveCourse.observe(this, new Observer<Course>() {
            @Override
            public void onChanged(Course course) {
                coursetoDelete = course;
                String startDate = convertDateToString(course.getStartDate());
                String endDate = convertDateToString(course.getEndDate());
                termId = course.getTermId();

                title.setText(course.getName());
                status.setText(course.getStatus());
                courseStart.setText(startDate);
                courseEnd.setText(endDate);
            }
        });

        Bundle extras = getIntent().getExtras();
        courseId = extras.getInt(Constants.COURSE_ID_KEY);
        courseViewModel.loadData(courseId);

    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }


    public void updateCourse(View view) {
        String sd = courseStart.getText().toString();
        String ed = courseEnd.getText().toString();
        try {
            Date startD = convertStringToDate(sd);
            Date endD = convertStringToDate(ed);
            currentCourse = new Course(courseId, title.getText().toString(), termId, status.getText().toString(), startD, endD);
            courseViewModel.updateCourse(currentCourse);
            Intent intent = new Intent(this, CourseDetails.class);
            intent.putExtra(Constants.COURSE_ID_KEY, courseId);
            startActivity(intent);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid Date", Toast.LENGTH_LONG).show();
        }

    }

    public void deleteCourse(View view) {
        courseViewModel.deleteCourse(coursetoDelete);

        Intent intent = new Intent(this, CourseList.class);
        intent.putExtra(Constants.TERM_ID_KEY, termId);
        startActivity(intent);
    }

    public void cancelCourseEdit(View view) {
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }


    public String convertDateToString(Date sDate)  {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateFormat.format(sDate);
        return date;
    }


    public Date convertStringToDate(String sDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        simpleDateFormat.setLenient(false);
        Date date = simpleDateFormat.parse(sDate);
        return date;
    }
}
