package com.example.c196studentscheduler.course_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.adapter.MentorListAdapter;
import com.example.c196studentscheduler.assessment_activities.AssessmentList;
import com.example.c196studentscheduler.entity.Course;
import com.example.c196studentscheduler.entity.Mentor;
import com.example.c196studentscheduler.mentor_activities.AddMentor;
import com.example.c196studentscheduler.note_activities.NoteList;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.CourseViewModel;
import com.example.c196studentscheduler.viewmodel.CourseViewModelFactory;
import com.example.c196studentscheduler.viewmodel.MentorViewModel;
import com.example.c196studentscheduler.viewmodel.MentorViewModelFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseDetails extends AppCompatActivity {

    @BindView(R.id.course_d_title)
    TextView title;
    @BindView(R.id.course_d_status)
    TextView status;
    @BindView(R.id.course_d_start)
    TextView courseStart;
    @BindView(R.id.course_d_end)
    TextView courseEnd;
    @BindView(R.id.course_d_assess_btn)
    Button viewAssess;
    @BindView(R.id.course_d_note_btn)
    Button viewNote;
    CourseViewModel courseViewModel;
    @BindView(R.id.mentor_list_recycler)
    RecyclerView mRecyclerView;

    private int termId;
    private int courseId;
    private List<Mentor> mentorData = new ArrayList<>();
    private MentorListAdapter mAdapter;
    MentorViewModelFactory mentorFactory;
    MentorViewModel mentorViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        setTitle(Constants.COURSE_DETAILS_TITLE);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {
        CourseViewModelFactory factory = new CourseViewModelFactory(this.getApplication());
        courseViewModel = ViewModelProviders.of(this, factory).get(CourseViewModel.class);
        courseViewModel.mLiveCourse.observe(this, new Observer<Course>() {
            @Override
            public void onChanged(Course course) {
                String startDate = convertDateToString(course.getStartDate());
                String endDate = convertDateToString(course.getEndDate());

                title.setText(course.getName());
                status.setText(course.getStatus());
                courseStart.setText(startDate);
                courseEnd.setText(endDate);
                termId = course.getTermId();
            }
        });

        Bundle extras = getIntent().getExtras();
        courseId = extras.getInt(Constants.COURSE_ID_KEY);
        courseViewModel.loadData(courseId);

        mentorFactory = new MentorViewModelFactory(this.getApplication(), courseId);
        mentorViewModel = ViewModelProviders.of(this, mentorFactory).get(MentorViewModel.class);

        final Observer<List<Mentor>> mentorObserver = new Observer<List<Mentor>>() {
            @Override
            public void onChanged(List<Mentor> mentors) {
                mentorData.clear();
                mentorData.addAll(mentors);
                if (mAdapter == null) {
                    mAdapter = new MentorListAdapter(mentorData, CourseDetails.this, mentorViewModel);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        };
        mentorViewModel.mMentorsByCourseId.observe(this, mentorObserver);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);

    }

    public void viewNotes(View view) {
        Intent intent = new Intent(this, NoteList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, CourseList.class);
        intent.putExtra(Constants.TERM_ID_KEY, termId);
        startActivity(intent);
    }

    public void editCourse(View view) {
        Intent intent = new Intent(this, EditCourse.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    public void goToAddMentor(View view) {
        Intent intent = new Intent(this, AddMentor.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    public void viewAssessments(View view) {
        Intent intent = new Intent(this, AssessmentList.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }


    public String convertDateToString(Date sDate)  {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateFormat.format(sDate);
        return date;
    }


//    public Date convertStringToDate(String sDate) throws ParseException {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        Date date = simpleDateFormat.parse(sDate);
//        return date;
//    }
}
