package com.example.c196studentscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196studentscheduler.entity.Mentor;

import java.util.List;

@Dao
public interface MentorDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMentor(Mentor mentor);

    @Update
    void updateMentor(Mentor mentor);

    @Delete
    void deleteMentor(Mentor mentor);

    @Query("SELECT * FROM Mentors")
    LiveData<List<Mentor>> getAllMentors();

    @Query("SELECT * FROM Mentors m JOIN Courses c ON m.MentorId = c.MentorId WHERE c.MentorId = :courseId")
    LiveData<List<Mentor>> getMentorsByCourseId(int courseId);

    @Query("SELECT * FROM Mentors WHERE MentorId = :mentorId")
    LiveData<Mentor> getMentorById(int mentorId);
}
