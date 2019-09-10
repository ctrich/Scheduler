package com.example.c196studentscheduler.Term_Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.RoomDatabase;

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

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);
        setTitle(Constants.TERM_EDIT_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        initViewModel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent(this, TermDetails.class);
        intent.putExtra(Constants.TERM_ID_KEY, termId);
        startActivity(intent);
        return true;
    }



    private void initViewModel() {



        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);

        termViewModel.mLiveTerm.observe(this, new Observer<Term>() {
            @Override
            public void onChanged(Term term) {
                deleteTerm = term;
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

        factory = new CourseViewModelFactory(this.getApplication(), termId);
        courseViewModel = ViewModelProviders.of(this, factory).get(CourseViewModel.class);
    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, TermDetails.class);
        intent.putExtra(Constants.TERM_ID_KEY, termId);
        startActivity(intent);
    }


    public void updateTerm(View view) {
        String sd = editSDate.getText().toString();
        String ed = editEDate.getText().toString();
        try {
            Date startD = convertStringToDate(sd);
            Date endD = convertStringToDate(ed);
            currentTerm = new Term(termId, editTitle.getText().toString(), startD, endD);
            termViewModel.updateTerm(currentTerm);
            Intent intent = new Intent(this, TermDetails.class);
            intent.putExtra(Constants.TERM_ID_KEY, termId);
            startActivity(intent);
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid Date", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteTerm(View view) {

        numberOfCourses = courseViewModel.getCourseCount(termId);
        Log.d(TAG, "deleteTerm: " + numberOfCourses + " " + termId);
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
