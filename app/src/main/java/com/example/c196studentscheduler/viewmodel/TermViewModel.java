package com.example.c196studentscheduler.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.c196studentscheduler.Database.SchedulerRepository;
import com.example.c196studentscheduler.entity.Term;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TermViewModel extends AndroidViewModel {


    public LiveData<List<Term>> mTerms;
    public MutableLiveData<Term> mLiveTerm = new MutableLiveData<>();
    private SchedulerRepository schedulerRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public TermViewModel(@NonNull Application application) {
        super(application);
        schedulerRepository = SchedulerRepository.getInstance(application.getApplicationContext());
        mTerms = schedulerRepository.mTerms;
    }

    public void addTerms(Term term) {
        schedulerRepository.createTerm(term);
    }

    public LiveData<List<Term>> getTerms() {
        return schedulerRepository.getAllTerms();
    }

    public void loadData(int termId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Term term = schedulerRepository.getTermByid(termId);
                mLiveTerm.postValue(term);
            }
        });

    }
}
