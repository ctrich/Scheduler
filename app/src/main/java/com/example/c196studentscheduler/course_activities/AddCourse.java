package com.example.c196studentscheduler.course_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.c196studentscheduler.R;

import com.example.c196studentscheduler.entity.Course;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.util.MyReceiver;
import com.example.c196studentscheduler.viewmodel.CourseViewModel;
import com.example.c196studentscheduler.viewmodel.CourseViewModelFactory;
import com.example.c196studentscheduler.viewmodel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCourse extends AppCompatActivity {
    private static final String TAG = "AddCourse";

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
    @BindView(R.id.add_course_start_reminder)
    CheckBox startReminder;
    @BindView(R.id.add_course_end_reminder)
    CheckBox endReminder;


    private Course currentCourse;
    CourseViewModel courseViewModel;
    TermViewModel termViewModel;
    private int termId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        dynamicButton();

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
            if (startReminder.isChecked()) {
                notification(sd, "course_start");
            }
            if (endReminder.isChecked()) {
                notification(ed, "course_end");
            }

            Intent intent = new Intent(this, CourseList.class);
            intent.putExtra(Constants.TERM_ID_KEY, termId);
            startActivity(intent);
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid Date", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("ResourceType")
    public void dynamicButton() {
        Button cancel = new Button(this);
        cancel.setText("Cancel");
        cancel.setId(100);

        ConstraintLayout constraintLayout1 = (ConstraintLayout) findViewById(R.id.add_course_second_constraint);


        ConstraintSet constraintSet = new ConstraintSet();
        constraintLayout1.addView(cancel,0);
        constraintSet.clone(constraintLayout1);
        constraintSet.connect(cancel.getId(),ConstraintSet.LEFT, R.id.add_course_save, ConstraintSet.RIGHT, 30);
        constraintSet.connect(cancel.getId(),ConstraintSet.BASELINE,R.id.add_course_save,ConstraintSet.BASELINE,0);
        constraintSet.applyTo(constraintLayout1);

        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(AddCourse.this, CourseList.class);
                intent.putExtra(Constants.TERM_ID_KEY, termId);
                startActivity(intent);
            }
        });
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

    public void notification(String date, String type) {
        int month =Integer.parseInt(date.substring(0, 2)) - 1;
        int day = Integer.parseInt(date.substring(3, 5));
        int year = Integer.parseInt(date.substring(6));
        String notificationContext = type;


        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year,month,day, 8, 30);

        Intent intent = new Intent(this, MyReceiver.class);
        intent.putExtra(Constants.NOTIFICATION_TYPE, notificationContext);
        intent.putExtra(Constants.COURSE_NAME, courseTitle.getText().toString());
        int random = (int)System.currentTimeMillis();
        PendingIntent sender= PendingIntent.getBroadcast(this,random,intent,0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);

    }

}
