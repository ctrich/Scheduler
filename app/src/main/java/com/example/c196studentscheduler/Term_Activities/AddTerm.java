package com.example.c196studentscheduler.Term_Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196studentscheduler.R;

import com.example.c196studentscheduler.entity.Term;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.TermViewModel;

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
public class AddTerm extends AppCompatActivity {
    private static final String TAG = "AddTerm";


    @BindView(R.id.add_term_title)
    EditText termTitle;
    @BindView(R.id.et_start_date)
    EditText startDate;
    @BindView(R.id.et_end_date)
    EditText endDate;

    private Term currentTerm;
    private TermViewModel termViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        setTitle(Constants.TERM_ADD_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        initViewModel();
    }

    /**
     * initialize the view model
     */
    private void initViewModel() {
        termViewModel = ViewModelProviders.of(this)
                .get(TermViewModel .class);
    }

    /**
     * Return the the term list when the devices back button is pressed
     */
    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
    }

    /**
     *
     * @param view
     * Insert a new term into the database
     */
    public void saveTerm(View view){
        //If all fields are not filled in show the user an error message
        if (termTitle.getText().toString().isEmpty() || startDate.getText().toString().isEmpty() || endDate.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        } else {
            String sd = startDate.getText().toString();
            String ed = endDate.getText().toString();
            try {
                Date startD = convertStringToDate(sd);
                Date endD = convertStringToDate(ed);
                //if the end date id before the start date show an error message
                if (endD.compareTo(startD) >= 0) {
                    currentTerm = new Term(termTitle.getText().toString(), startD, endD);
                    termViewModel.addTerms(currentTerm);
                    Intent intent = new Intent(this, TermList.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "End Date can't be before Start Date", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                Toast.makeText(this, "Invalid Date", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void cancelAdd(View view) {
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
    }

    /**
     *
     * @param sDate
     * @return a date in MM/DD/YYYY format
     * @throws ParseException
     */
    public Date convertStringToDate(String sDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        simpleDateFormat.setLenient(false);
        Date date = simpleDateFormat.parse(sDate);
        return date;
    }


}