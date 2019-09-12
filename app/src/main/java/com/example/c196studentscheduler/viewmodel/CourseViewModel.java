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

/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
public class CourseViewModel extends AndroidViewModel {
    //Used to display course data in the edit activity
    public MutableLiveData<Course> mLiveCourse = new MutableLiveData<>();
    public LiveData<List<Course>> mCourses;
    public LiveData<List<Course>> mCourseByTerm;
    private SchedulerRepository schedulerRepository;
    //Used to run functions in a background thread
    private Executor executor = Executors.newSingleThreadExecutor();



    public CourseViewModel(@NonNull Application application, int termId) {
        super(application);
        schedulerRepository = SchedulerRepository.getInstance(application.getApplicationContext());
        mCourseByTerm = schedulerRepository.getCoursesByTermId(termId);
        mCourses = schedulerRepository.mCourses;
    }

    /**
     *
     * @param termId
     * @return the number of courses that belongs to the termId
     */
    public int getCourseCount(int termId) {
        return schedulerRepository.numCoursesInTerm(termId);
    }

    /**
     *
     * @param course
     * Adds a new course to the database
     */
    public void addCourse(Course course) {
        executor.execute(() -> schedulerRepository.createCourse(course));
    }

    /**
     *
     * @param course
     * Updates and existing course in the database
     */
    public void updateCourse(Course course) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.updateCourse(course);
            }
        });
    }

    /**
     *
     * @param course
     * Deletes a course from the database
     */
    public void deleteCourse(Course course) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.deleteCourse(course);
            }
        });
    }

    /**
     *
     * @param courseId
     * Displays the course data in course details
     */
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
