package com.example.c196studentscheduler.Term_Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.TermDetails;
import com.example.c196studentscheduler.entity.Term;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditTerm extends AppCompatActivity {

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
    private boolean mNewTerm;
    private int termId;
    private Term deleteTerm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);
        setTitle(Constants.TERM_EDIT_TITLE);

        ButterKnife.bind(this);
        initViewModel();
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
        if (extras == null) {

            mNewTerm = true;
        } else {

            termId = extras.getInt(Constants.TERM_ID_KEY);
            termViewModel.loadData(termId);
        }
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
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, TermDetails.class);
        intent.putExtra(Constants.TERM_ID_KEY, termId);
        startActivity(intent);
    }

    public void deleteTerm(View view) {
        
        termViewModel.deleteTerm(deleteTerm);
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);

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
        Date date = simpleDateFormat.parse(sDate);
        return date;
    }
}
