package com.example.c196studentscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196studentscheduler.entity.Term;

import java.util.List;
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
@Dao
public interface TermDAO {
    @Insert
    void insert(Term term);

    @Update
    void updateTerm(Term term);

    @Delete
    void deleteTerm(Term term);

    @Query("SELECT * FROM Terms ORDER BY StartDate ASC")
    LiveData<List<Term>> getAllTerms();

    @Query("SELECT * FROM Terms WHERE TermId = :termId")
    Term getTermById(int termId);

    @Query("SELECT * FROM Terms")
    List<Term> getTerms();
}
