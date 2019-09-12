package com.example.c196studentscheduler.course_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
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
    @BindView(R.id.course_edit_reminder_start)
    CheckBox startReminder;
    @BindView(R.id.course_edit_reminder_end)
    CheckBox endReminder;

    private int courseId;
    private int termId;
    private Course coursetoDelete;
    private Course currentCourse;
    private boolean editing;

    CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        setTitle(Constants.COURSE_EDIT_TITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            editing = savedInstanceState.getBoolean(Constants.EDITING_KEY);
        }
        initViewModel();
    }

    /**
     *
     * @param outState
     * Handle configuration changes
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(Constants.EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
        return true;
    }

    /**
     * initialize view model
     */
    private void initViewModel() {
        CourseViewModelFactory factory = new CourseViewModelFactory(this.getApplication());
        courseViewModel = ViewModelProviders.of(this, factory).get(CourseViewModel.class);

        courseViewModel.mLiveCourse.observe(this, course -> {
           if (course != null) {
               coursetoDelete = course;
               termId = course.getTermId();
               //Don't reset edit texts on configuration change
               if (!editing) {
                   String startDate = convertDateToString(course.getStartDate());
                   String endDate = convertDateToString(course.getEndDate());

                   title.setText(course.getName());
                   status.setText(course.getStatus());
                   courseStart.setText(startDate);
                   courseEnd.setText(endDate);
               }
           }
        });

        Bundle extras = getIntent().getExtras();
        courseId = extras.getInt(Constants.COURSE_ID_KEY);
        courseViewModel.loadData(courseId);

    }

    /**
     * Go to course details when the devices back button is pressed
     */
    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    /**
     *
     * @param view
     * Update a course in the database
     */
    public void updateCourse(View view) {
        //If all fields are not filled in show the user a message
        if (title.getText().toString().isEmpty() || status.getText().toString().isEmpty() || courseStart.getText().toString().isEmpty() || courseEnd.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        } else {
            String sd = courseStart.getText().toString();
            String ed = courseEnd.getText().toString();
            try {
                //convert the edit text string to dates
                Date startD = convertStringToDate(sd);
                Date endD = convertStringToDate(ed);
                //if the end date is before the start date show the user an error message
                if (endD.compareTo(startD) >= 0) {
                    currentCourse = new Course(courseId, title.getText().toString(), termId, status.getText().toString(), startD, endD);
                    //call the update course from the viewmodel
                    courseViewModel.updateCourse(currentCourse);

                    if (startReminder.isChecked()) {
                        notification(sd, "course_start");
                    }
                    if (endReminder.isChecked()) {
                        notification(ed, "course_end");
                    }

                    Intent intent = new Intent(this, CourseDetails.class);
                    intent.putExtra(Constants.COURSE_ID_KEY, courseId);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "End Date can't be before Start Date", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(this, "Invalid Date", Toast.LENGTH_LONG).show();
            }
        }

    }

    /**
     * Delete the current course
     * @param view
     */
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

    /**
     *
     * @param sDate
     * @return a string that holds a date int MM/DD/YYYY format
     */
    public String convertDateToString(Date sDate)  {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateFormat.format(sDate);
        return date;
    }

    /**
     *
     * @param sDate
     * @return A Date in MM/DD/YYYY format
     * @throws ParseException
     */
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
        intent.putExtra(Constants.COURSE_NAME, title.getText().toString());
        //Create a random int to use as the request code so it doesn't interfere with other notifications
        int random = (int)System.currentTimeMillis();
        PendingIntent sender= PendingIntent.getBroadcast(this,random,intent,0);
        //Set an alarm to wake the device and display a notification to a certain time
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);

    }

}
