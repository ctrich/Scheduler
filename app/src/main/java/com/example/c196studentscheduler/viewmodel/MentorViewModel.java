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

public class MentorViewModel extends AndroidViewModel {

    public MutableLiveData<Mentor> mLiveMentor = new MutableLiveData<>();
    private SchedulerRepository schedulerRepository;
    private Executor executor = Executors.newSingleThreadExecutor();
    public LiveData<List<Mentor>> mMentorsByCourseId;

    public MentorViewModel(@NonNull Application application, int courseId) {
        super(application);
        schedulerRepository = SchedulerRepository.getInstance(application.getApplicationContext());
        mMentorsByCourseId = schedulerRepository.getMentorByCourseId(courseId);
    }

    public void addMentor(Mentor mentor) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.createMentor(mentor);
            }
        });
    }

    public void updateMentor(Mentor mentor) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.updateMentor(mentor);
            }
        });
    }

    public void deleteMentor(Mentor mentor) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.deleteMentor(mentor);
            }
        });
    }

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
