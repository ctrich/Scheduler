package com.example.c196studentscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196studentscheduler.entity.Assessment;

import java.util.List;

@Dao
public interface AssessmentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(Assessment assessment);

    @Update
    void updateAssessment(Assessment assessment);

    @Delete
    void deleteAssessment(Assessment assessment);

    @Query("SELECT * FROM ASSESSMENTS")
    LiveData<List<Assessment>> getAllAssessment();

    @Query("SELECT * FROM Assessments WHERE CourseId = :courseId")
    LiveData<List<Assessment>> getAssessmentsByCourseId(int courseId);

    @Query("SELECT * FROM Assessments WHERE AssessmentId = :assessmentId")
    LiveData<Assessment> getAssessmentById(int assessmentId);
}
