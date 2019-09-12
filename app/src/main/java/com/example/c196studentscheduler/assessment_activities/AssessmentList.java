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
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
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
        //Set the back button in the actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    /**
     *
     * @param item
     * Sends the courseId when the back button in the actionbar is pressed
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
        startActivity(intent);
        return true;
    }

    private void initViewModel() {
        //Observe Live data and update the list when changes are made
        final Observer<List<Assessment>> assessmentObserver = new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> assessments) {
                //clear the array list
                assessmentData.clear();
                //add all assessment from the live data list to the array list
                assessmentData.addAll(assessments);
                //set up the adapter to display the list in the recycler view
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

    /**
     * initialize the recycler view
     */
    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(divider);
    }
}
