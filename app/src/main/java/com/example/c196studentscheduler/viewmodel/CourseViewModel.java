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

    public MutableLiveData<Course> mLiveCourse = new MutableLiveData<>();
    public LiveData<List<Course>> mCourses;
    public LiveData<List<Course>> mCourseByTerm;
    private SchedulerRepository schedulerRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public CourseViewModel(@NonNull Application application, int termId) {
        super(application);
        schedulerRepository = SchedulerRepository.getInstance(application.getApplicationContext());
        mCourseByTerm = schedulerRepository.getCoursesByTermId(termId);
        mCourses = schedulerRepository.mCourses;
    }



    public  void addCourse(Course course) {
        executor.execute(() -> schedulerRepository.createCourse(course));
    }

    public void updateCourse(Course course) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.updateCourse(course);
            }
        });
    }

    public void deleteCourse(Course course) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.deleteCourse(course);
            }
        });
    }

    public void loadData(int courseId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Course course = schedulerRepository.getCourseByid(courseId);
                mLiveCourse.postValue(course);
            }
        });

    }
}
