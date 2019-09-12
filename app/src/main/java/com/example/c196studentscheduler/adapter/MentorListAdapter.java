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
import com.example.c196studentscheduler.entity.Mentor;
import com.example.c196studentscheduler.mentor_activities.EditMentor;
import com.example.c196studentscheduler.util.Constants;
import com.example.c196studentscheduler.viewmodel.MentorViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 *
 * Responsible for displaying all mentors for selected course in a recyclerview
 */
public class MentorListAdapter extends RecyclerView.Adapter<MentorListAdapter.ViewHolder> {

    private final List<Mentor> mMentor;
    private final Context context;
    private final MentorViewModel viewModel;


    public MentorListAdapter(List<Mentor> mMentor, Context context, MentorViewModel viewModel) {
        this.mMentor = mMentor;
        this.context = context;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public MentorListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mentor_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MentorListAdapter.ViewHolder holder, int position) {
        final Mentor mentor= mMentor.get(position);
        //set text view text
        holder.mName.setText(mMentor.get(position).getName());
        holder.mPhone.setText(mMentor.get(position).getPhone());
        holder.mEmail.setText(mMentor.get(position).geteMail());
        //Deletes the mentor
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.deleteMentor(mentor);
            }
        });

        holder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditMentor.class);
                intent.putExtra(Constants.MENTOR_ID_KEY, mentor.getMentorId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     *
     * @return the number of mentors for the selected course
     */
    @Override
    public int getItemCount() {
        return mMentor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mentor_list_name)
        TextView mName;
        @BindView(R.id.mentor_list_email)
        TextView mEmail;
        @BindView(R.id.mentor_list_phone)
        TextView mPhone;
        @BindView(R.id.mentor_list_delete_fab)
        FloatingActionButton mDelete;
        @BindView(R.id.mentor_list_edit_fab)
        FloatingActionButton mEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
