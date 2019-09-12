package com.example.c196studentscheduler.assessment_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.entity.Assessment;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.AssessViewModel;
import com.example.c196studentscheduler.viewmodel.AssessViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
public class AssessmentDetails extends AppCompatActivity {
    @BindView(R.id.assess_details_title)
    TextView title;
    @BindView(R.id.assess_details_type)
    TextView type;
    @BindView(R.id.assess_details_goal)
    TextView goal;
    @BindView(R.id.assess_details_edit_fab)
    FloatingActionButton editFab;

    private AssessViewModelFactory factory;
    private AssessViewModel assessViewModel;
    private int courseId;
    private int assessmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        setTitle(Constants.ASSESSMENT_DETAILS_TITLE);
        //Set the back button in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        initViewModel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent(this, AssessmentList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
        return true;
    }

    private void initViewModel() {

        factory = new AssessViewModelFactory(this.getApplication());
        //Instantiate a viewModel so we can access the database
        assessViewModel = ViewModelProviders.of(this, factory).get(AssessViewModel.class);
        //Observe the Mutable Live Data to set the textViews
        assessViewModel.mLiveAssessment.observe(this, new Observer<Assessment>() {
            @Override
            public void onChanged(Assessment assessment) {
                String goalDate = convertDateToString(assessment.getDueDate());
                title.setText(assessment.getName());
                type.setText(assessment.getType());
                goal.setText(goalDate);
                courseId = assessment.getCourseId();
            }
        });
        //Get the assessmentId so we know which assessment to display
        Bundle extras = getIntent().getExtras();
        assessmentId = extras.getInt(Constants.ASSESSMENT_ID_KEY);
        assessViewModel.loadData(assessmentId);
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
     * When the user clicks the edit button, go to the edit assessment activity
     * and pass the assessmentId
     */
    public void editAssessment(View view) {
        Intent intent = new Intent(this, EditAssessment.class);
        intent.putExtra(Constants.ASSESSMENT_ID_KEY, assessmentId);
        startActivity(intent);
    }

    public void goBack(View view) {
            Intent intent = new Intent(this, AssessmentList.class);
            intent.putExtra(Constants.COURSE_ID_KEY, courseId);
            startActivity(intent);
    }

    /**
     *
     * @param sDate
     * @return a date as a string in MM/DD/YYYY format
     */
    public String convertDateToString(Date sDate)  {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateFormat.format(sDate);
        return date;
    }


}
