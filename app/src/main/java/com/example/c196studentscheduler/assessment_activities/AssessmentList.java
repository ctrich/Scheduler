package com.example.c196studentscheduler.assessment_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.adapter.AssessmentListAdapter;
import com.example.c196studentscheduler.course_activities.CourseDetails;
import com.example.c196studentscheduler.entity.Assessment;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.AssessViewModel;
import com.example.c196studentscheduler.viewmodel.AssessViewModelFactory;

import java.util.ArrayList;
import java.util.List;



import butterknife.BindView;
import butterknife.ButterKnife;

public class AssessmentList extends AppCompatActivity {

    @BindView(R.id.assessment_list_recycler)
    RecyclerView recyclerView;


    private AssessmentListAdapter mAdapter;
    private List<Assessment> assessmentData = new ArrayList<>();
    private AssessViewModelFactory factory;
    private AssessViewModel assessViewModel;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        setTitle(Constants.ASSESSMENT_LIST_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
        return true;
    }

    private void initViewModel() {
        final Observer<List<Assessment>> assessmentObserver = new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> assessments) {
                assessmentData.clear();
                assessmentData.addAll(assessments);
                if (mAdapter == null) {
                    mAdapter = new AssessmentListAdapter(assessmentData, AssessmentList.this);
                    recyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        };
        Bundle extras = getIntent().getExtras();
        courseId = extras.getInt(Constants.COURSE_ID_KEY);

        factory = new AssessViewModelFactory(this.getApplication(), courseId);
        assessViewModel = ViewModelProviders.of(this, factory).get(AssessViewModel.class);
        assessViewModel.mAssessmentByCourseId.observe(this, assessmentObserver);
    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    public void showAddAssessment(View view) {
        Intent intent = new Intent(this, AddAssessment.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(divider);
    }
}
