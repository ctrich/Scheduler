package com.example.c196studentscheduler.Term_Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.activities.MainActivity;
import com.example.c196studentscheduler.adapter.TermListAdapter;
import com.example.c196studentscheduler.entity.Term;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.TermViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermList extends AppCompatActivity {
    private static final String TAG = "TermList";
    @BindView(R.id.term_recycler_view)
    RecyclerView mRecyclerView;
    private List<Term> termsData = new ArrayList<>();
    private TermListAdapter mAdapter;
    private TermViewModel termListViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        setTitle(Constants.TERM_LIST_TITLE);


        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();

    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
        termListViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        termListViewModel.mTerms.observe(this, termObserver);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
    }
}
