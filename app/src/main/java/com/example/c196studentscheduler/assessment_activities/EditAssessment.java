package com.example.c196studentscheduler.assessment_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.viewmodel.AssessViewModel;
import com.example.c196studentscheduler.viewmodel.AssessViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditAssessment extends AppCompatActivity {
    @BindView(R.id.assess_edit_title)
    EditText title;
    @BindView(R.id.assess_edit_goal)
    EditText goal;
    @BindView(R.id.assess_edit_radio_group)
    RadioGroup radioGroup;

    private AssessViewModelFactory factory;
    private AssessViewModel assessViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);
        ButterKnife.bind(this);
        initViewModel();
    }

    private void initViewModel() {
        factory = new AssessViewModelFactory(this.getApplication());
        assessViewModel = ViewModelProviders.of(this, factory).get(CourseViewModel.class);

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
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
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
        Date date = simpleDateFormat.parse(sDate);
        return date;
    }
}
