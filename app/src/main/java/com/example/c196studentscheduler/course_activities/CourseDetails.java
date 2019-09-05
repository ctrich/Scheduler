package com.example.c196studentscheduler.course_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.entity.Course;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.CourseViewModel;
import com.example.c196studentscheduler.viewmodel.CourseViewModelFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseDetails extends AppCompatActivity {

    @BindView(R.id.course_d_title)
    TextView title;
    @BindView(R.id.course_d_status)
    TextView status;
    @BindView(R.id.course_d_start)
    TextView courseStart;
    @BindView(R.id.course_d_end)
    TextView courseEnd;
    @BindView(R.id.course_d_assess_btn)
    Button viewAssess;
    @BindView(R.id.course_d_note_btn)
    Button viewNote;
    CourseViewModel courseViewModel;
    private int termId;
    private int courseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
//        setTitle(Constants.);

        ButterKnife.bind(this);
        initViewModel();
    }

    private void initViewModel() {
        CourseViewModelFactory factory = new CourseViewModelFactory(this.getApplication());
        courseViewModel = ViewModelProviders.of(this, factory).get(CourseViewModel.class);
        courseViewModel.mLiveCourse.observe(this, new Observer<Course>() {
            @Override
            public void onChanged(Course course) {
                String startDate = convertDateToString(course.getStartDate());
                String endDate = convertDateToString(course.getEndDate());

                title.setText(course.getName());
                status.setText(course.getStatus());
                courseStart.setText(startDate);
                courseEnd.setText(endDate);
                termId = course.getTermId();
            }
        });
        Bundle extras = getIntent().getExtras();
        courseId = extras.getInt(Constants.COURSE_ID_KEY);
        courseViewModel.loadData(courseId);
    }

//    public void showCourses(View view) {
//        Intent intent = new Intent(this, CourseList.class);
//        intent.putExtra(Constants.TERM_ID_KEY, termId);
//        startActivity(intent);
//    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, CourseList.class);
        intent.putExtra(Constants.TERM_ID_KEY, termId);
        startActivity(intent);
    }

    public void editCourse(View view) {
        Intent intent = new Intent(this, EditCourse.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }


//    public void updateTerm(View view) {
//        String sd = editSDate.getText().toString();
//        String ed = editEDate.getText().toString();
//        try {
//            Date startD = convertStringToDate(sd);
//            Date endD = convertStringToDate(ed);
//            currentTerm = new Term(termId, editTitle.getText().toString(), startD, endD);
//            termViewModel.updateTerm(currentTerm);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Intent intent = new Intent(this, TermList.class);
//        startActivity(intent);
//    }

    public String convertDateToString(Date sDate)  {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateFormat.format(sDate);
        return date;
    }


//    public Date convertStringToDate(String sDate) throws ParseException {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        Date date = simpleDateFormat.parse(sDate);
//        return date;
//    }
}
