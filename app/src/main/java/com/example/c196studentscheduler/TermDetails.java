package com.example.c196studentscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.Term_Activities.EditTerm;
import com.example.c196studentscheduler.Term_Activities.TermList;
import com.example.c196studentscheduler.course_activities.CourseList;
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

public class TermDetails extends AppCompatActivity {

    @BindView(R.id.term_details_title)
    TextView editTitle;
    @BindView(R.id.term_details_start)
    TextView editSDate;
    @BindView(R.id.term_details_end)
    TextView editEDate;



    private Term currentTerm;
    private TermViewModel termViewModel;
    private int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        setTitle(Constants.TERM_DETAILS_TITLE);
        ButterKnife.bind(this);
        initViewModel();
    }


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

    public void showCourses(View view) {
        Intent intent = new Intent(this, CourseList.class);
        intent.putExtra(Constants.TERM_ID_KEY, termId);
        startActivity(intent);
    }

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


//    public void updateTerm(View view) {
//        String sd = editSDate.getText().toString();
//        String ed = editEDate.getText().toString();
//        try {
//            Date startD = convertStringToDate(sd);
//            Date endD = convertStringToDate(ed);
//            currentTerm = new Term(termId, editTitle.getText().toString(), startD, endD);
//            termViewModel.updateTerm(currentTerm);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Intent intent = new Intent(this, TermList.class);
//        startActivity(intent);
//    }

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
