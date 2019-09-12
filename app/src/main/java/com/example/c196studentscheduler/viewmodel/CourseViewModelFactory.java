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
public class CourseViewModelFactory implements ViewModelProvider.Factory {

    private int searchTermId;
    private Application application;
    //create a view model factory that allows us to pass the termId to the view model
    public CourseViewModelFactory(Application application, int searchTermId) {
        this.searchTermId = searchTermId;
        this.application = application;
    }

    public CourseViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return (T) new CourseViewModel(application, searchTermId);

    }
}
