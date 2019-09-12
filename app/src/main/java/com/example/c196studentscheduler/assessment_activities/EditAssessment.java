package com.example.c196studentscheduler.assessment_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.entity.Assessment;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.util.MyReceiver;
import com.example.c196studentscheduler.viewmodel.AssessViewModel;
import com.example.c196studentscheduler.viewmodel.AssessViewModelFactory;

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
public class EditAssessment extends AppCompatActivity {
    @BindView(R.id.assess_edit_title)
    EditText title;
    @BindView(R.id.assess_edit_goal)
    EditText goal;
    @BindView(R.id.assess_edit_radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.radio_objective)
    RadioButton objective;
    @BindView(R.id.radio_performance)
    RadioButton performance;
    @BindView(R.id.assess_edit_reminder)
    CheckBox reminder;

    AssessViewModelFactory factory;
    AssessViewModel assessViewModel;
    private Assessment assessmentToDelete;
    private int courseId;
    private int assessmentId;
    private Assessment currentAssessment;
    private RadioButton radioButton;
    String date;
    private boolean editing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);
        setTitle(Constants.ASSESSMENT_EDIT_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        //Set editing to true if the configuration has changed
        if (savedInstanceState != null) {
            editing = savedInstanceState.getBoolean(Constants.EDITING_KEY);
        }
        initViewModel();
    }

    /**
     * @param outState
     * save the value true if the configuration has changed
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(Constants.EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent(this, AssessmentDetails.class);
        intent.putExtra(Constants.ASSESSMENT_ID_KEY, assessmentId);
        startActivity(intent);
        return true;
    }

    /**
     * initialize the view model.
     * set all textviews and radio buttons
     */
    private void initViewModel() {
        factory = new AssessViewModelFactory(this.getApplication());
        assessViewModel = ViewModelProviders.of(this, factory).get(AssessViewModel.class);

        assessViewModel.mLiveAssessment.observe(this, assessment -> {
            if (assessment != null) {
                assessmentToDelete = assessment;
                courseId = assessment.getCourseId();
                String goalDate = convertDateToString(assessment.getDueDate());
                //Dont reset the edit texts if the configuration changed
                if (!editing) {
                    if (assessment.getType().equals("Objective")) {
                        objective.setChecked(true);
                    } else {
                        performance.setChecked(true);
                    }
                    title.setText(assessment.getName());
                    goal.setText(goalDate);
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        assessmentId = extras.getInt(Constants.ASSESSMENT_ID_KEY);
        assessViewModel.loadData(assessmentId);

    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, AssessmentDetails.class);
        intent.putExtra(Constants.ASSESSMENT_ID_KEY, assessmentId);
        startActivity(intent);
    }

    /**
     * update and assessment in the database
     * @param view
     */
    public void updateAssessment(View view) {
        //if all fields are not filled in show a message and exit the function
        if (title.getText().toString().isEmpty() || goal.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        } else {
            date = goal.getText().toString();
            //Get the selected radio button
            int selectedId = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(selectedId);

            try {
                //Convert the String date to a Date
                Date goalD = convertStringToDate(date);
                currentAssessment = new Assessment(assessmentId, courseId, title.getText().toString(), radioButton.getText().toString(), goalD);
                assessViewModel.updateAssessment(currentAssessment);
                //Call the notification function if the user selected reminder
                if (reminder.isChecked()) {
                    notification();
                }
                //return to the assessment details activity after the update
                Intent intent = new Intent(this, AssessmentDetails.class);
                intent.putExtra(Constants.ASSESSMENT_ID_KEY, assessmentId);
                startActivity(intent);
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(this, "Invalid Date", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Delete the current assessment
     * @param view
     */
    public void deleteAssessment(View view) {
        assessViewModel.deleteAssessment(assessmentToDelete);

        Intent intent = new Intent(this, AssessmentList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    public void cancelAssessEdit(View view) {
        Intent intent = new Intent(this, AssessmentDetails.class);
        intent.putExtra(Constants.ASSESSMENT_ID_KEY, assessmentId);
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

    /**
     * Set a notification for the assessments goal date
     */
    public void notification() {
        //get the month, day, and year from the goal string
        int month =Integer.parseInt(date.substring(0, 2)) - 1;
        int day = Integer.parseInt(date.substring(3, 5));
        int year = Integer.parseInt(date.substring(6));
        String notificationContext = "Assessment";

        //Create a calendar and set the date for the assessment goal
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year,month,day, 8, 30);

        Intent intent=new Intent(this, MyReceiver.class);
        //Send the notification type and assessment title to display in the notification
        intent.putExtra(Constants.NOTIFICATION_TYPE, notificationContext);
        intent.putExtra(Constants.ASSESSMENT_NAME, title.getText().toString());
        int random = (int)System.currentTimeMillis();
        PendingIntent sender= PendingIntent.getBroadcast(this,random,intent,0);
        //Set an alarm to wake the device and display a notification to a certain time
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);

    }
}
