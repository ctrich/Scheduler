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
public class MentorViewModelFactory implements ViewModelProvider.Factory {

    private int courseId;
    private Application application;
    //Creates a view model factory that allows us to pass the courseId to the view model
    public MentorViewModelFactory(Application application, int courseId) {
        this.application = application;
        this.courseId = courseId;
    }

    public MentorViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MentorViewModel(application, courseId);
    }
}
