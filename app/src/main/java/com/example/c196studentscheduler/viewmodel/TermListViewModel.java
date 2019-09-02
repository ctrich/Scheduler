package com.example.c196studentscheduler.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.c196studentscheduler.Database.SchedulerRepository;
import com.example.c196studentscheduler.entity.Term;

import java.util.List;

public class TermListViewModel extends AndroidViewModel {
    private static final String TAG = "TermListViewModel";


    public LiveData<List<Term>> mTerms;
    private SchedulerRepository schedulerRepository;

    public TermListViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "TermListViewModel: running" + application);
        schedulerRepository = SchedulerRepository.getInstance(application.getApplicationContext());
        mTerms = schedulerRepository.mTerms;
    }

    public void addTerms(Term term) {
        schedulerRepository.createTerm(term);
    }

    public LiveData<List<Term>> getTerms() {
        return schedulerRepository.getAllTerms();
    }
}
