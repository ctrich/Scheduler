package com.example.c196studentscheduler.assessment_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.entity.Assessment;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.util.MyReceiver;
import com.example.c196studentscheduler.viewmodel.AssessViewModel;
import com.example.c196studentscheduler.viewmodel.AssessViewModelFactory;

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
public class AddAssessment extends AppCompatActivity {
    private static final String TAG = "AddAssessment";

    @BindView(R.id.assess_add_title)
    TextView title;
    @BindView(R.id.assess_add_goal)
    TextView goal;
    @BindView(R.id.assess_add_reminder)
    CheckBox reminder;
    @BindView(R.id.assess_add_save)
    Button save;
    @BindView(R.id.assess_add_cancel)
    Button cancel;
    @BindView(R.id.assess_radio_group)
    RadioGroup radioGroup;

    private AssessViewModelFactory factory;
    private AssessViewModel assessViewModel;
    private int courseId;
    private Assessment currentAssessment;
    private RadioButton radioButton;
    private String aGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        //Set the title in the actionbar
        getSupportActionBar().setTitle(Constants.ASSESSMENT_ADD_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        initViewModel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //When the back button in the actionbar is presses, send the course id to the AssessmentList activity
        Intent intent = new Intent(this, AssessmentList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
        return true;
    }

    private void initViewModel() {

        factory = new AssessViewModelFactory(this.getApplication());
        //Instantiate a viewModel so we can access the database
        assessViewModel = ViewModelProviders.of(this, factory)
                .get(AssessViewModel.class);
        //Get the courseId so we can set it with the assessment
        Bundle extras = getIntent().getExtras();
        courseId = extras.getInt(Constants.COURSE_ID_KEY);
    }

    /**
     *
     * @param view
     *
     * save a new assessment to the database
     */
    public void saveAssess(View view) {
        //check to see if all fields are filled in
        if (title.getText().toString().isEmpty() || goal.getText().toString().isEmpty()) {
            //display a message if all fields are not filled in and exit the function
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        } else {
            aGoal = goal.getText().toString();
            String assessTitle = title.getText().toString();
            int selectedId = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(selectedId);
            if (aGoal.length() == 10) {
                try {
                    //Convert the String to Date
                    Date date = convertStringToDate(aGoal);
                    //Create a new assessment to insert into the database
                    currentAssessment = new Assessment(courseId, assessTitle, radioButton.getText().toString(), date);
                    assessViewModel.addAssessment(currentAssessment);

                    if (reminder.isChecked()) {
                        notification();
                    }
                    //Pass the assessments courseID to the assessment list activity
                    Intent intent = new Intent(this, AssessmentList.class);
                    intent.putExtra(Constants.COURSE_ID_KEY, courseId);
                    startActivity(intent);
                } catch (ParseException e) {
                    Toast.makeText(this, "Invalid Date format", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Invalid Date", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * When the user presses the devices back button go to
     * the assessmentList activity and pass the courseId
     */
    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, AssessmentList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    /**
     *
     * @param view
     * Cancel the assessment add and pass go to the assessment list activity
     */
    public void cancelAdd(View view) {
        Intent intent = new Intent(this, AssessmentList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    /**
     *
     * @param sDate
     * @return a date in MM/DD/YYYY format
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
    public void notification() {
        //get the month, day, and year from the goal string
        int month =Integer.parseInt(aGoal.substring(0, 2)) - 1;
        int day = Integer.parseInt(aGoal.substring(3, 5));
        int year = Integer.parseInt(aGoal.substring(6));
        String notificationContext = "Assessment";

        //Create a calendar and set the date for the assessment goal
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year,month,day, 8, 30);

        Intent intent=new Intent(AddAssessment.this, MyReceiver.class);
        //Send the notification type and assessment title to display in the notification
        intent.putExtra(Constants.NOTIFICATION_TYPE, notificationContext);
        intent.putExtra(Constants.ASSESSMENT_NAME, title.getText().toString());
        //Create a random int to use as the request code so it doesn't interfere with other notifications
        int random = (int)System.currentTimeMillis();
        PendingIntent sender= PendingIntent.getBroadcast(AddAssessment.this,random,intent,0);
        //Set an alarm to wake the device and display a notification to a certain time
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);

    }

}


