package com.example.c196studentscheduler.assessment_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.c196studentscheduler.viewmodel.AssessViewModel;
import com.example.c196studentscheduler.viewmodel.AssessViewModelFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAssessment extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        getSupportActionBar().setTitle(Constants.ASSESSMENT_ADD_TITLE);
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
        assessViewModel = ViewModelProviders.of(this, factory)
                .get(AssessViewModel.class);

        Bundle extras = getIntent().getExtras();
        courseId = extras.getInt(Constants.COURSE_ID_KEY);
    }

    public void saveAssess(View view) {
        String aGoal = goal.getText().toString();
        String assessTitle = title.getText().toString();
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);
        try {
            Date date = convertStringToDate(aGoal);

            currentAssessment = new Assessment(courseId, assessTitle, radioButton.getText().toString(), date);
            assessViewModel.addAssessment(currentAssessment);

            Intent intent = new Intent(this, AssessmentList.class);
            intent.putExtra(Constants.COURSE_ID_KEY, courseId);
            startActivity(intent);
        }catch (ParseException e) {
            Toast.makeText(this, "Invalid Date", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, AssessmentList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }


    public void cancelAdd(View view) {
        Intent intent = new Intent(this, AssessmentList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    public Date convertStringToDate(String sDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        simpleDateFormat.setLenient(false);
        Date date = simpleDateFormat.parse(sDate);
        return date;
    }

}
