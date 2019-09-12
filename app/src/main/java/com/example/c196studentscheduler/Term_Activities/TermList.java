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
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
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

    /**
     * Load the main activity when the devices back button id pressed
     */
    @Override
    public void onBackPressed () {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     *
     * @param view
     * Display the add term activity
     */
    public void showAddTerm(View view) {
        Log.d(TAG, "showTerm: running");
        Intent intent = new Intent(this, AddTerm.class);
        startActivity(intent);
    }

    /**
     * initialize the view model and set the adapter
     * to display the the list of terms in the recycler view
     */
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

    /**
     * initialize the recycler view
     */
    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
    }
}
