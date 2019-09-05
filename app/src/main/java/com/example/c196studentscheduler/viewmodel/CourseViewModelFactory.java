package com.example.c196studentscheduler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CourseViewModelFactory implements ViewModelProvider.Factory {

    private int searchTermId;
    private Application application;

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
