package com.example.c196studentscheduler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.c196studentscheduler.Database.SchedulerRepository;
import com.example.c196studentscheduler.entity.Course;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseViewModel extends AndroidViewModel {

    public LiveData<List<Course>> mCourses;
    public MutableLiveData<Course> mLiveTerm = new MutableLiveData<>();
    private SchedulerRepository schedulerRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public CourseViewModel(@NonNull Application application) {
        super(application);
        schedulerRepository = SchedulerRepository.getInstance(application.getApplicationContext());
        mCourses = schedulerRepository.mCourses;
    }

    public  void addCourse(Course course) {
        executor.execute(() -> schedulerRepository.createCourse(course));
    }
}
