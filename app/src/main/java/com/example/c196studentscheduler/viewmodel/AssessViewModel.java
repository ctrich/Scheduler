package com.example.c196studentscheduler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.c196studentscheduler.Database.SchedulerRepository;
import com.example.c196studentscheduler.entity.Assessment;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AssessViewModel extends AndroidViewModel {

    private SchedulerRepository schedulerRepository;
    private Executor executor = Executors.newSingleThreadExecutor();
    public LiveData<List<Assessment>> mAssessmentByCourseId;

    public AssessViewModel(@NonNull Application application, int courseId) {
        super(application);
        schedulerRepository = SchedulerRepository.getInstance(application.getApplicationContext());
        mAssessmentByCourseId = schedulerRepository.getAssessmentByCourseId(courseId);
    }
}
