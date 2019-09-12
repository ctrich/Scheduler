package com.example.c196studentscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196studentscheduler.entity.Assessment;

import java.util.List;
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
@Dao
public interface AssessmentDAO {

    @Insert
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
    Assessment getAssessmentById(int assessmentId);
}
