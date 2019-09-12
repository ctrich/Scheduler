package com.example.c196studentscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196studentscheduler.entity.Course;

import java.util.List;
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
@Dao
public interface CourseDAO {

    @Insert
    void insertCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Update
    void updateCourse(Course course);

    @Query("SELECT * FROM Courses")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM Courses WHERE TermId = :termId")
    LiveData<List<Course>> getCoursesByTerm(int termId);

    @Query("SELECT * FROM Courses WHERE CourseId = :courseId")
    Course getCourseById(int courseId);

    @Query("SELECT Name FROM Terms WHERE TermId = :termID")
    String getTermNameByTermId(int termID);

    @Query("SELECT COUNT(*) FROM Courses where TermId = :termId")
    int getCoursesForTermDelete(Integer termId);

}
