package com.example.c196studentscheduler.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.Term_Activities.TermList;
import com.example.c196studentscheduler.course_activities.CourseList;
import com.example.c196studentscheduler.util.Constants;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(Constants.HOME_SCREEN_TITLE);
    }

    public void showTerm(View view) {

        Intent i = new Intent(this, TermList.class);
        startActivity(i);
    }
}
