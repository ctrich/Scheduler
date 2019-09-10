package com.example.c196studentscheduler.mentor_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.course_activities.CourseDetails;
import com.example.c196studentscheduler.entity.Mentor;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.MentorViewModel;
import com.example.c196studentscheduler.viewmodel.MentorViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mentor);
        setTitle(Constants.MENTOR_EDIT_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        initViewModel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
        return true;
    }

    private void initViewModel() {
        factory = new MentorViewModelFactory(this.getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(MentorViewModel.class);

        viewModel.mLiveMentor.observe(this, new Observer<Mentor>() {
            @Override
            public void onChanged(Mentor mentor) {
                courseId = mentor.getCourseID();

                name.setText(mentor.getName());
                phone.setText(mentor.getPhone());
                email.setText(mentor.geteMail());
            }
        });

        Bundle extras = getIntent().getExtras();
        mentorId = extras.getInt(Constants.MENTOR_ID_KEY);
        viewModel.loadData(mentorId);

    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }


    public void updateMentor(View view) {

        currentMentor = new Mentor(mentorId, courseId, name.getText().toString(), phone.getText().toString(), email.getText().toString());
        viewModel.updateMentor(currentMentor);

        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    public void cancelMentorEdit(View view) {
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

}
