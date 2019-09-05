package com.example.c196studentscheduler.viewmodel;

import android.app.Application;

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
    public List<Term> listTerms;
    public MutableLiveData<Term> mLiveTerm = new MutableLiveData<>();
    private SchedulerRepository schedulerRepository;
    private Executor executor = Executors.newSingleThreadExecutor();



    public TermViewModel(@NonNull Application application) {
        super(application);
        schedulerRepository = SchedulerRepository.getInstance(application.getApplicationContext());
        mTerms = schedulerRepository.mTerms;
        listTerms = schedulerRepository.listTerms;
    }



    public void addTerms(Term term) {
        schedulerRepository.createTerm(term);
    }

    public void updateTerm(Term term) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.updateTerm(term);
            }
        });
    }

    public void deleteTerm(Term term) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.deleteTerm(term);
            }
        });
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
