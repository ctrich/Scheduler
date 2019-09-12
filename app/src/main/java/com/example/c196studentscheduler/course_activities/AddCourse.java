package com.example.c196studentscheduler.course_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196studentscheduler.R;

import com.example.c196studentscheduler.entity.Course;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.util.MyReceiver;
import com.example.c196studentscheduler.viewmodel.CourseViewModel;
import com.example.c196studentscheduler.viewmodel.CourseViewModelFactory;
import com.example.c196studentscheduler.viewmodel.TermViewModel;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
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
        if (courseTitle.getText().toString().isEmpty() || courseSatus.getText().toString().isEmpty() || sDate.getText().toString().isEmpty() || eDate.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        } else {

            String courseName = courseTitle.getText().toString();
            String status = courseSatus.getText().toString();
            String sd = sDate.getText().toString();
            String ed = eDate.getText().toString();
            //Check for two digit month, two digit day and four digit year
            if (sd.length() == 10 && ed.length() == 10) {
                try {
                    Date startD = convertStringToDate(sd);
                    Date endD = convertStringToDate(ed);
                    if (endD.compareTo(startD) >= 0) {
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
                    } else {
                        Toast.makeText(this, "End Date can't be before Start Date", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    Toast.makeText(this, "Invalid Date format", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Invalid Date", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Create a button programmatically
     */
    @SuppressLint("ResourceType")
    public void dynamicButton() {
        //Create a button
        Button cancel = new Button(this);
        cancel.setText("Cancel");
        cancel.setId(100);
        //get the activities layout
        ConstraintLayout constraintLayout1 = (ConstraintLayout) findViewById(R.id.add_course_second_constraint);


        ConstraintSet constraintSet = new ConstraintSet();
        constraintLayout1.addView(cancel,0);
        constraintSet.clone(constraintLayout1);
        //Set the button constraints
        constraintSet.connect(cancel.getId(),ConstraintSet.LEFT, R.id.add_course_save, ConstraintSet.RIGHT, 30);
        constraintSet.connect(cancel.getId(),ConstraintSet.BASELINE,R.id.add_course_save,ConstraintSet.BASELINE,0);
        //Apply the constraints
        constraintSet.applyTo(constraintLayout1);
        //If the button is clicked cancel the add course and return to the course list activity
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

    /**
     * Set a notification for the assessments goal date
     */
    public void notification(String date, String type) {
        //get the month, day, and year from the goal string
        int month =Integer.parseInt(date.substring(0, 2)) - 1;
        int day = Integer.parseInt(date.substring(3, 5));
        int year = Integer.parseInt(date.substring(6));
        String notificationContext = type;

        //Create a calendar and set the date for the assessment goal
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year,month,day, 8, 30);

        Intent intent = new Intent(this, MyReceiver.class);
        //Send the notification type and assessment title to display in the notification
        intent.putExtra(Constants.NOTIFICATION_TYPE, notificationContext);
        intent.putExtra(Constants.COURSE_NAME, courseTitle.getText().toString());
        //Create a random int to use as the request code so it doesn't interfere with other notifications
        int random = (int)System.currentTimeMillis();
        PendingIntent sender= PendingIntent.getBroadcast(this,random,intent,0);
        //Set an alarm to wake the device and display a notification to a certain time
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);

    }

}
