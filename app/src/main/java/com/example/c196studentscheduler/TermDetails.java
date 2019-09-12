package com.example.c196studentscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.c196studentscheduler.Term_Activities.EditTerm;
import com.example.c196studentscheduler.Term_Activities.TermList;
import com.example.c196studentscheduler.course_activities.CourseList;
import com.example.c196studentscheduler.entity.Term;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.TermViewModel;

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
public class TermDetails extends AppCompatActivity {

    @BindView(R.id.term_details_title)
    TextView editTitle;
    @BindView(R.id.term_details_start)
    TextView editSDate;
    @BindView(R.id.term_details_end)
    TextView editEDate;

    private TermViewModel termViewModel;
    private int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        setTitle(Constants.TERM_DETAILS_TITLE);
        //Displays the back button in the actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        initViewModel();
    }

    /**
     * initialize the view model and set the textViews text
     */
    private void initViewModel() {
        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);

        termViewModel.mLiveTerm.observe(this, new Observer<Term>() {
            @Override
            public void onChanged(Term term) {

                String startDate = convertDateToString(term.getStartDate());
                String endDate = convertDateToString(term.getEndDate());
                editTitle.setText(term.getName());
                editSDate.setText(startDate);
                editEDate.setText(endDate);
                termId = term.getTermId();
            }
        });
        Bundle extras = getIntent().getExtras();
            termId = extras.getInt(Constants.TERM_ID_KEY);
            termViewModel.loadData(termId);
    }

    /**
     *
     * @param view
     * Show the list of courses when the user taps on the button
     */
    public void showCourses(View view) {
        Intent intent = new Intent(this, CourseList.class);
        intent.putExtra(Constants.TERM_ID_KEY, termId);
        startActivity(intent);
    }

    /**
     * Load term list when the devices back button is pressed
     */
    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
    }

    public void editTerm(View view) {
        Intent intent = new Intent(this, EditTerm.class);
        intent.putExtra(Constants.TERM_ID_KEY, termId);
        startActivity(intent);
    }

    /**
     *
     * @param sDate
     * @return a string that holds a date in MM/DD/YYYY format
     */
    public String convertDateToString(Date sDate)  {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateFormat.format(sDate);
        return date;
    }

}
