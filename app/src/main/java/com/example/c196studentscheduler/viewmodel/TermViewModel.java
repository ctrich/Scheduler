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
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
public class TermViewModel extends AndroidViewModel {


    public LiveData<List<Term>> mTerms;
    public List<Term> listTerms;
    //Used to pass term data to the edit activity
    public MutableLiveData<Term> mLiveTerm = new MutableLiveData<>();
    private SchedulerRepository schedulerRepository;
    //used to run a function on a background thread
    private Executor executor = Executors.newSingleThreadExecutor();



    public TermViewModel(@NonNull Application application) {
        super(application);
        schedulerRepository = SchedulerRepository.getInstance(application.getApplicationContext());
        mTerms = schedulerRepository.mTerms;
        listTerms = schedulerRepository.listTerms;
    }


    /**
     *
     * @param term
     * Adds a new term to the database
     */
    public void addTerms(Term term) {
        schedulerRepository.createTerm(term);
    }

    /**
     * @param term
     * Updates an existing term in the database
     */
    public void updateTerm(Term term) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.updateTerm(term);
            }
        });
    }

    /**
     * Deletes a term from the database
     * @param term
     */
    public void deleteTerm(Term term) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.deleteTerm(term);
            }
        });
    }

    /**
     * Passes the term data for the edit activity
     * @param termId
     */
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
