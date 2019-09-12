package com.example.c196studentscheduler.Term_Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.TermDetails;
import com.example.c196studentscheduler.entity.Term;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.CourseViewModel;
import com.example.c196studentscheduler.viewmodel.CourseViewModelFactory;
import com.example.c196studentscheduler.viewmodel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
public class EditTerm extends AppCompatActivity {
    private static final String TAG = "EditTerm";

    @BindView(R.id.et_edit_title)
    EditText editTitle;
    @BindView(R.id.et_edit_sDate)
    EditText editSDate;
    @BindView(R.id.et_edit_eDate)
    EditText editEDate;
    @BindView(R.id.edit_save_btn)
    Button saveBtn;
    @BindView(R.id.edit_cancel_btn)
    Button cancelBtn;
    @BindView(R.id.delete_term_fab)
    FloatingActionButton deleteFab;


    private Term currentTerm;
    private TermViewModel termViewModel;
    private int termId;
    private Term deleteTerm;
    private CourseViewModelFactory factory;
    private CourseViewModel courseViewModel;
    private int numberOfCourses;
    private boolean editing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);
        setTitle(Constants.TERM_EDIT_TITLE);
        //Display the back button in the actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            editing = savedInstanceState.getBoolean(Constants.EDITING_KEY);
        }
        initViewModel();
    }

    /**
     *
     * @param outState
     * Handle configuration changes
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(Constants.EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent(this, TermDetails.class);
        intent.putExtra(Constants.TERM_ID_KEY, termId);
        startActivity(intent);
        return true;
    }


    /**
     * initialize the view model and set the text view text
     */
    private void initViewModel() {
        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);

        termViewModel.mLiveTerm.observe(this, term -> {
            if (term != null) {
                deleteTerm = term;
                termId = term.getTermId();
                if (!editing) {
                    String startDate = convertDateToString(term.getStartDate());
                    String endDate = convertDateToString(term.getEndDate());
                    editTitle.setText(term.getName());
                    editSDate.setText(startDate);
                    editEDate.setText(endDate);
                }

            }
        });
        Bundle extras = getIntent().getExtras();
        termId = extras.getInt(Constants.TERM_ID_KEY);
        termViewModel.loadData(termId);

        factory = new CourseViewModelFactory(this.getApplication(), termId);
        courseViewModel = ViewModelProviders.of(this, factory).get(CourseViewModel.class);
    }
    /**
     * Return to the term Details when the devices back button is pressed
     */
    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, TermDetails.class);
        intent.putExtra(Constants.TERM_ID_KEY, termId);
        startActivity(intent);
    }

    /**
     *
     * @param view
     * Update an existing term in the database
     */
    public void updateTerm(View view) {
        //If all fields are not filled in display an error message
        if (editTitle.getText().toString().isEmpty() || editEDate.getText().toString().isEmpty() || editSDate.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        } else {

            String sd = editSDate.getText().toString();
            String ed = editEDate.getText().toString();
            //Check for two digit month, two digit day and four digit year
            if (sd.length() == 10 && ed.length() == 10) {
                try {
                    Date startD = convertStringToDate(sd);
                    Date endD = convertStringToDate(ed);
                    //If the end date is before the start date display an error message
                    if (endD.compareTo(startD) >= 0) {
                        currentTerm = new Term(termId, editTitle.getText().toString(), startD, endD);
                        termViewModel.updateTerm(currentTerm);
                        Intent intent = new Intent(this, TermDetails.class);
                        intent.putExtra(Constants.TERM_ID_KEY, termId);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "End Date can't be before Start Date", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    //If the date is not entered in the right format display an error message
                    Toast.makeText(this, "Invalid Date format", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Invalid Date", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     *
     * @param view
     * Delete the current term
     */
    public void deleteTerm(View view) {
        //Get the number of courses that are assigned to the current term
        numberOfCourses = courseViewModel.getCourseCount(termId);
        Log.d(TAG, "deleteTerm: " + numberOfCourses + " " + termId);
        //If there are any courses assigned to this term it cannot be deleted
        if (numberOfCourses == 0){
            termViewModel.deleteTerm(deleteTerm);
            Intent intent = new Intent(this, TermList.class);
            startActivity(intent);
        }else {
                Toast.makeText(this, "Terms with courses assigned cannot be deleted", Toast.LENGTH_LONG).show();
        }

    }

    public void cancelEdit(View view) {
        Intent intent = new Intent(this, TermDetails.class);
        intent.putExtra(Constants.TERM_ID_KEY, termId);
        startActivity(intent);
    }


    public String convertDateToString(Date sDate)  {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateFormat.format(sDate);
        return date;
    }


    public Date convertStringToDate(String sDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        simpleDateFormat.setLenient(false);
        Date date = simpleDateFormat.parse(sDate);
        return date;
    }
}
