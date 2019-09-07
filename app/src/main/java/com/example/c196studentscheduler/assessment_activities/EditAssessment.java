package com.example.c196studentscheduler.assessment_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.entity.Assessment;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.AssessViewModel;
import com.example.c196studentscheduler.viewmodel.AssessViewModelFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    AssessViewModelFactory factory;
    AssessViewModel assessViewModel;
    private Assessment assessmentToDelete;
    private int courseId;
    private int assessmentId;
    private Assessment currentAssessment;
    private RadioButton radioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);
        ButterKnife.bind(this);
        initViewModel();
    }

    private void initViewModel() {
        factory = new AssessViewModelFactory(this.getApplication());
        assessViewModel = ViewModelProviders.of(this, factory).get(AssessViewModel.class);

        assessViewModel.mLiveAssessment.observe(this, new Observer<Assessment>() {
            @Override
            public void onChanged(Assessment assessment) {
                assessmentToDelete = assessment;

                courseId = assessment.getCourseId();
                String goalDate = convertDateToString(assessment.getDueDate());

                if (assessment.getType().equals("Objective")) {
                    objective.setChecked(true);
                } else {
                    performance.setChecked(true);
                }
                title.setText(assessment.getName());
                goal.setText(goalDate);
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


    public void updateCourse(View view) {
        String date = goal.getText().toString();

        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);

        try {
            Date goalD = convertStringToDate(date);
            currentAssessment = new Assessment(assessmentId, courseId, title.getText().toString(), radioButton.getText().toString(), goalD);
            assessViewModel.updateAssessment(currentAssessment);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, AssessmentDetails.class);
        intent.putExtra(Constants.ASSESSMENT_ID_KEY, assessmentId);
        startActivity(intent);
    }

    public void deleteCourse(View view) {
        assessViewModel.deleteAssessment(assessmentToDelete);

        Intent intent = new Intent(this, AssessmentList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    public void cancelCourseEdit(View view) {
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
        Date date = simpleDateFormat.parse(sDate);
        return date;
    }
}
