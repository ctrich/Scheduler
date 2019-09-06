package com.example.c196studentscheduler.mentor_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.course_activities.CourseDetails;
import com.example.c196studentscheduler.entity.Mentor;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.MentorViewModel;
import com.example.c196studentscheduler.viewmodel.MentorViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMentor extends AppCompatActivity {
    @BindView(R.id.mentor_add_name)
    EditText name;
    @BindView(R.id.mentor_add_phone)
    EditText phone;
    @BindView(R.id.mentor_add_email)
    EditText email;
    @BindView(R.id.mentor_add_save)
    Button saveBtn;
    @BindView(R.id.mentor_add_cancel)
    Button cancelBtn;

    private MentorViewModel mentorViewModel;
    private int courseId;
    private Mentor currentMentor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mentor);
        setTitle(Constants.MENTOR_ADD_TITLE);

        ButterKnife.bind(this);
        initViewModel();
    }

    private void initViewModel() {
        MentorViewModelFactory factory = new MentorViewModelFactory(this.getApplication());
        mentorViewModel = ViewModelProviders.of(this, factory)
                .get(MentorViewModel.class);
        Bundle extras = getIntent().getExtras();
        courseId = extras.getInt(Constants.COURSE_ID_KEY);
    }

    @Override
    public void onBackPressed () {
        backToCourseDetails();
    }

    public void saveMentor(View view) {

        currentMentor = new Mentor(courseId, name.getText().toString(), phone.getText().toString(), email.getText().toString());
        mentorViewModel.addMentor(currentMentor);

        backToCourseDetails();
    }

    public void cancelMentorAdd(View view) {
        backToCourseDetails();
    }

    private void backToCourseDetails() {
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

}
