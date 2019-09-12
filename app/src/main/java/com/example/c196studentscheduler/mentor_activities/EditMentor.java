package com.example.c196studentscheduler.mentor_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.course_activities.CourseDetails;
import com.example.c196studentscheduler.entity.Mentor;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.MentorViewModel;
import com.example.c196studentscheduler.viewmodel.MentorViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
public class EditMentor extends AppCompatActivity {
    @BindView(R.id.mentor_edit_name)
    EditText name;
    @BindView(R.id.mentor_edit_phone)
    EditText phone;
    @BindView(R.id.mentor_edit_email)
    EditText email;

    private MentorViewModelFactory factory;
    private MentorViewModel viewModel;
    private Mentor currentMentor;
    private int courseId;
    private int mentorId;
    private boolean editing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mentor);
        setTitle(Constants.MENTOR_EDIT_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            editing = savedInstanceState.getBoolean(Constants.EDITING_KEY);
        }
        initViewModel();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(Constants.EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
        return true;
    }

    /**
     * initialize the view model and set the text view text
     */
    private void initViewModel() {
        factory = new MentorViewModelFactory(this.getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(MentorViewModel.class);

        viewModel.mLiveMentor.observe(this, mentor -> {
            if (mentor != null) {
                courseId = mentor.getCourseID();
                if (!editing) {
                    name.setText(mentor.getName());
                    phone.setText(mentor.getPhone());
                    email.setText(mentor.geteMail());
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        mentorId = extras.getInt(Constants.MENTOR_ID_KEY);
        viewModel.loadData(mentorId);

    }

    /**
     * Load Course Details when the devices back button is pressed
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
     * Upadte an existing mentor
     */
    public void updateMentor(View view) {
       if (name.getText().toString().isEmpty() || phone.getText().toString().isEmpty() || email.getText().toString().isEmpty()) {
           Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
           return;
       } else {
           currentMentor = new Mentor(mentorId, courseId, name.getText().toString(), phone.getText().toString(), email.getText().toString());
           viewModel.updateMentor(currentMentor);

           Intent intent = new Intent(this, CourseDetails.class);
           intent.putExtra(Constants.COURSE_ID_KEY, courseId);
           startActivity(intent);
       }
    }

    public void cancelMentorEdit(View view) {
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

}
