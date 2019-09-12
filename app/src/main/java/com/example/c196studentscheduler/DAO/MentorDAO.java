package com.example.c196studentscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196studentscheduler.entity.Mentor;

import java.util.List;
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
@Dao
public interface MentorDAO {
    @Insert
    void insertMentor(Mentor mentor);

    @Update
    void updateMentor(Mentor mentor);

    @Delete
    void deleteMentor(Mentor mentor);

    @Query("SELECT * FROM Mentors")
    LiveData<List<Mentor>> getAllMentors();


    @Query("SELECT * FROM Mentors WHERE MentorId = :mentorId")
    Mentor getMentorById(int mentorId);

    @Query("SELECT * FROM Mentors WHERE CourseId = :courseId")
    LiveData<List<Mentor>> getMentorsByCourseId(int courseId);
}
