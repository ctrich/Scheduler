package com.example.c196studentscheduler.Term_Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.c196studentscheduler.AddTerm;
import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.adapter.TermListAdapter;
import com.example.c196studentscheduler.entity.Term;
import com.example.c196studentscheduler.viewmodel.TermListViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermList extends AppCompatActivity {
    private static final String TAG = "TermList";
    @BindView(R.id.term_recycler_view)
    RecyclerView mRecyclerView;
    private List<Term> termsData = new ArrayList<>();
    private TermListAdapter mAdapter;
    private TermListViewModel termListViewModel;

    private Date date = new Date();
    private Term term1 = new Term("Summer", date, date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);



        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();

    }

    public void showAddTerm(View view) {
        Log.d(TAG, "showTerm: running");
        Intent intent = new Intent(this, AddTerm.class);
        startActivity(intent);
    }

    private void initViewModel() {

        final Observer<List<Term>> termObserver = terms -> {
            termsData.clear();
            termsData.addAll(terms);
            if (mAdapter == null) {
                mAdapter = new TermListAdapter(termsData, TermList.this);
                mRecyclerView.setAdapter(mAdapter);
            }else {
                mAdapter.notifyDataSetChanged();
            }

        };

        Log.d(TAG, "initViewModel: running");
        termListViewModel = ViewModelProviders.of(this).get(TermListViewModel.class);
        termListViewModel.addTerms(term1);
        termListViewModel.mTerms.observe(this, termObserver);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

    }
}
