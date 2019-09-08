package com.example.c196studentscheduler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NoteViewModelFactory implements ViewModelProvider.Factory {

    private int courseId;
    private Application application;

    public NoteViewModelFactory(Application application, int courseId) {
        this.courseId = courseId;
        this.application = application;
    }

    public NoteViewModelFactory(Application application) {
        this.application = application;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NoteViewModel(application, courseId);
    }
}
