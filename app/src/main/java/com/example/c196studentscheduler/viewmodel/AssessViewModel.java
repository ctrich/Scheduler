package com.example.c196studentscheduler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.c196studentscheduler.Database.SchedulerRepository;

import com.example.c196studentscheduler.entity.Assessment;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
public class AssessViewModel extends AndroidViewModel {
    //Used for the edit activity
    public MutableLiveData<Assessment> mLiveAssessment = new MutableLiveData<>();
    private SchedulerRepository schedulerRepository;
    //Used to run the function on a background thread
    private Executor executor = Executors.newSingleThreadExecutor();

    public LiveData<List<Assessment>> mAssessmentByCourseId;

    public AssessViewModel(@NonNull Application application, int courseId) {
        super(application);
        schedulerRepository = SchedulerRepository.getInstance(application.getApplicationContext());
        mAssessmentByCourseId = schedulerRepository.getAssessmentByCourseId(courseId);
    }

    /**
     *
     * @param assessment
     * Uses the repository to add an assessment to the database
     */
    public void addAssessment(Assessment assessment) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.createAssessment(assessment);
            }
        });
    }

    /**
     *
     * @param assessmentId
     * Used to display the assessment details in the assessment details activity
     */
    public void loadData(int assessmentId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Assessment assessment = schedulerRepository.getAssessmentByid(assessmentId);
                mLiveAssessment.postValue(assessment);
            }
        });

    }

    /**
     *
     * @param assessment
     * Uses the repository to update an existing assessment
     */
    public void updateAssessment(Assessment assessment) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.updateAssessment(assessment);
            }
        });
    }

    /**
     *
     * @param assessment
     * Uses the repository to delete an assessment
     */
    public void deleteAssessment(Assessment assessment) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.deleteAssessment(assessment);
            }
        });
    }
}
