package com.example.c196studentscheduler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.c196studentscheduler.Database.SchedulerRepository;
import com.example.c196studentscheduler.entity.Mentor;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
public class MentorViewModel extends AndroidViewModel {
    //Display the mentor data in the edit activity
    public MutableLiveData<Mentor> mLiveMentor = new MutableLiveData<>();
    private SchedulerRepository schedulerRepository;
    //Used to run a function on a background thread
    private Executor executor = Executors.newSingleThreadExecutor();
    public LiveData<List<Mentor>> mMentorsByCourseId;

    public MentorViewModel(@NonNull Application application, int courseId) {
        super(application);
        schedulerRepository = SchedulerRepository.getInstance(application.getApplicationContext());
        mMentorsByCourseId = schedulerRepository.getMentorByCourseId(courseId);
    }

    /**
     *
     * @param mentor
     * Add a mentor to the database
     */
    public void addMentor(Mentor mentor) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.createMentor(mentor);
            }
        });
    }

    /**
     *
     * @param mentor
     * Update an existing mentor in the database
     */
    public void updateMentor(Mentor mentor) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.updateMentor(mentor);
            }
        });
    }

    /**
     *
     * @param mentor
     * Delete a mentor from the database
     */
    public void deleteMentor(Mentor mentor) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.deleteMentor(mentor);
            }
        });
    }

    /**
     *
     * @param mentorId
     * Used to display the mentor data in the edit activity
     */
    public void loadData(int mentorId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Mentor mentor = schedulerRepository.getMentorByid(mentorId);
                mLiveMentor.postValue(mentor);
            }
        });

    }
}
