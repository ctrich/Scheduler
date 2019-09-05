package com.example.c196studentscheduler.adapter;

import android.content.Context;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.course_activities.CourseDetails;
import com.example.c196studentscheduler.entity.Course;

import com.example.c196studentscheduler.util.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {

    private final List<Course> mCourse;
    private final Context context;

    public CourseListAdapter(List<Course> mCourse, Context context) {
        this.mCourse = mCourse;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseListAdapter.ViewHolder holder, int position) {
        final Course course = mCourse.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

        String sDate = sdf.format(mCourse.get(position).getStartDate());
        String eDate = sdf.format(mCourse.get(position).getEndDate());

        holder.CourseTitle.setText(mCourse.get(position).getName());
        holder.courseStart.setText(sDate);
        holder.courseEnd.setText(eDate);
        holder.courseStatus.setText(mCourse.get(position).getStatus());

        holder.fab.setOnClickListener(view -> {
            Intent intent = new Intent(context, CourseDetails.class);
            intent.putExtra(Constants.COURSE_ID_KEY, course.getCourseId());
            context.startActivity(intent);
        });
   }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mCourse.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_course_cv)
        CardView cv;
        @BindView(R.id.list_course_end)
        TextView courseEnd;
        @BindView(R.id.list_course_start)
        TextView courseStart;
        @BindView(R.id.list_course_title)
        TextView CourseTitle;
        @BindView(R.id.list_course_fab)
        FloatingActionButton fab;
        @BindView(R.id.list_course_status)
        TextView courseStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
