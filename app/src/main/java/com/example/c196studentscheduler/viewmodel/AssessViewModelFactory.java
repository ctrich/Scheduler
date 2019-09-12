package com.example.c196studentscheduler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
public class AssessViewModelFactory implements ViewModelProvider.Factory {

    private int courseID;
    private Application application;
    //creates a view model factory that allows us to pass the courseId to the viewmodel
    public AssessViewModelFactory(Application application, int courseID) {
        this.application = application;
        this.courseID = courseID;
    }

    public AssessViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AssessViewModel(application, courseID);
    }
}
