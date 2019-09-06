package com.example.c196studentscheduler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.entity.Assessment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
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
