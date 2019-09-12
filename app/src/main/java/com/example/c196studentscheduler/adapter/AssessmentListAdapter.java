package com.example.c196studentscheduler.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.assessment_activities.AssessmentDetails;
import com.example.c196studentscheduler.entity.Assessment;
import com.example.c196studentscheduler.util.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 *
 * Responsible for displaying all assessments for selected course in a recyclerview
 */
public class AssessmentListAdapter extends RecyclerView.Adapter<AssessmentListAdapter.ViewHolder> {

    private final List<Assessment> mAssessment;
    private final Context context;

    public AssessmentListAdapter(List<Assessment> mAssessment, Context context) {
        this.mAssessment = mAssessment;
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.assessment_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentListAdapter.ViewHolder holder, int position) {
        holder.title.setText(mAssessment.get(position).getName());
        holder.type.setText(mAssessment.get(position).getType());

        //Floating action button on the assessment list activity that leads to the assessment details page. Passes assessmentId using intent.
        holder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AssessmentDetails.class);
                intent.putExtra(Constants.ASSESSMENT_ID_KEY, mAssessment.get(position).getAssessmentId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        //returns the number of assessment for the course
        return mAssessment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.assessment_list_title)
        TextView title;
        @BindView(R.id.assessment_list_type)
        TextView type;
        @BindView(R.id.assessment_list_view_fab)
        FloatingActionButton fab;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
