package com.example.c196studentscheduler.Database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.c196studentscheduler.DAO.AssessmentDAO;
import com.example.c196studentscheduler.DAO.CourseDAO;
import com.example.c196studentscheduler.DAO.MentorDAO;
import com.example.c196studentscheduler.DAO.NoteDAO;
import com.example.c196studentscheduler.DAO.TermDAO;
import com.example.c196studentscheduler.entity.Assessment;
import com.example.c196studentscheduler.entity.Course;
import com.example.c196studentscheduler.entity.Mentor;
import com.example.c196studentscheduler.entity.Note;
import com.example.c196studentscheduler.entity.Term;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SchedulerRepository {
    private static final String TAG = "SchedulerRepository";

    private TermDAO termDAO;

    private CourseDAO courseDAO;
    private MentorDAO mentorDAO;
    private NoteDAO noteDAO;
    private AssessmentDAO assessmentDAO;

    public LiveData<List<Term>> mTerms;
    private SchedulerRoomDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

//    private LiveData<List<Term>> allTerms;

//    private LiveData<List<Course>> allCourses;
//    private LiveData<List<Assessment>> allAssessments;
//    private LiveData<List<Note>> allNotes;
//    private LiveData<List<Mentor>> allMentors;


//    public SchedulerRepository(TermDAO termDAO, CourseDAO courseDAO, MentorDAO mentorDAO, NoteDAO noteDAO, AssessmentDAO assessmentDAO) {
//        this.termDAO = termDAO;
//        this.courseDAO = courseDAO;
//        this.mentorDAO = mentorDAO;
//        this.noteDAO = noteDAO;
//        this.assessmentDAO = assessmentDAO;
//    }

    private static SchedulerRepository ourInstance;

    public static SchedulerRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new SchedulerRepository(context);
        }
        return ourInstance;
    }

    private SchedulerRepository(Context context) {
        Log.d(TAG, "SchedulerRepository: running" + context);
        mDb = SchedulerRoomDatabase.getDatabase(context);
        termDAO = mDb.termDAO();
        mTerms = getAllTerms();
    }



    //return all
    public LiveData<List<Term>> getAllTerms() {
        return termDAO.getAllTerms();
    }

    public LiveData<List<Course>> getAllCourses() {
        return courseDAO.getAllCourses();
    }

    public LiveData<List<Mentor>> getAllMentors() {
        return mentorDAO.getAllMentors();
    }

    public LiveData<List<Note>> getAllNotes() {
        return noteDAO.getAllNotes();
    }

    public LiveData<List<Assessment>> getAllAssessments() {
        return assessmentDAO.getAllAssessment();
    }

        //inserts

        public void createTerm(Term term) {
            executor.execute(() -> mDb.termDAO().insert(term));
        }

        public void createCourse(Course course) {
            courseDAO.insertCourse(course);
        }

        public void createMentor(Mentor mentor) {
            mentorDAO.insertMentor(mentor);
        }

        public void createNote(Note note) {
            noteDAO.isertNote(note);
        }
        public void createAssessment(Assessment assessment) {
            assessmentDAO.insertAssessment(assessment);
        }



    //updates
    public void updateTerm(Term term) {
        termDAO.updateTerm(term);
    }

    public void updateCourse(Course course) {
        courseDAO.updateCourse(course);
    }

    public void updateMentor(Mentor mentor) {
        mentorDAO.updateMentor(mentor);
    }

    public void updateNote(Note note) {
        noteDAO.updateNote(note);
    }

    public void updateAssessment(Assessment assessment) {
        assessmentDAO.updateAssessment(assessment);
    }

    //deletes
    public void deleteTerm(Term term) {
        termDAO.deleteTerm(term);
    }

    public void deleteCourse(Course course) {
        courseDAO.deleteCourse(course);
    }

    public void deleteMentor(Mentor mentor) {
        mentorDAO.deleteMentor(mentor);
    }

    public void deleteNote(Note note) {
        noteDAO.deleteNote(note);
    }

    public void deleteAssessment(Assessment assessment) {
        assessmentDAO.deleteAssessment(assessment);
    }

    //search by id
    public Term getTermByid(int termId) {
        return termDAO.getTermById(termId);
    }

    public LiveData<Course> getCourseByid(int courseId) {
        return courseDAO.getCourseById(courseId);
    }

    public LiveData<Mentor> getMentorByid(int mentorId) {
        return mentorDAO.getMentorById(mentorId);
    }

    public LiveData<Note> getNoteByid(int noteId) {
        return noteDAO.getNoteById(noteId);
    }

    public LiveData<Assessment> getAssessmentByid(int assessmentId) {
        return assessmentDAO.getAssessmentById(assessmentId);
    }

    //search by related objects
    public LiveData<List<Course>> getCoursesByTermId(int termId) {
        return courseDAO.getCoursesByTerm(termId);
    }

    public LiveData<List<Mentor>> getMentorsByCourseId(int courseId) {
        return mentorDAO.getMentorsByCourseId(courseId);
    }

    public LiveData<List<Note>> getNotesByCourseId(int courseId) {
        return noteDAO.getNoteByCourseId(courseId);
    }

    public LiveData<List<Assessment>> getAssessmentByCourseId(int courseId) {
        return assessmentDAO.getAssessmentsByCourseId(courseId);
    }

}
