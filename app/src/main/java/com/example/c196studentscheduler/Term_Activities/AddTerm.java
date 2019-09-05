package com.example.c196studentscheduler.Term_Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.c196studentscheduler.R;

import com.example.c196studentscheduler.entity.Term;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.TermViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        ButterKnife.bind(this);
        initViewModel();
    }

    private void initViewModel() {
        termViewModel = ViewModelProviders.of(this)
                .get(TermViewModel .class);
    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
    }

    public void saveTerm(View view) throws ParseException {
        Log.d(TAG, "saveTerm: ");
        String sd = startDate.getText().toString();
        String ed = endDate.getText().toString();

        Date startD = convertStringToDate(sd);
        Date endD = convertStringToDate(ed);
        currentTerm = new Term(termTitle.getText().toString(), startD, endD);

        termViewModel.addTerms(currentTerm);

        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
    }

    public void cancelAdd(View view) {
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
    }

    public Date convertStringToDate(String sDate) throws ParseException {
        Log.d(TAG, "convertStringToDate: " + sDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = simpleDateFormat.parse(sDate);
        return date;
    }


}