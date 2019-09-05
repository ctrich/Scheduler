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

import com.example.c196studentscheduler.TermDetails;
import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.entity.Term;
import com.example.c196studentscheduler.util.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermListAdapter extends RecyclerView.Adapter<TermListAdapter.ViewHolder> {

    private final List<Term> mTerms;
    private final Context context;

    public TermListAdapter(List<Term> mTerms, Context context) {
        this.mTerms = mTerms;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.term_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Term term = mTerms.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

        String sDate = sdf.format(mTerms.get(position).getStartDate());
        String eDate = sdf.format(mTerms.get(position).getEndDate());

        holder.mTermTitle.setText(mTerms.get(position).getName());
        holder.mTermStart.setText(sDate);
        holder.mTermEnd.setText(eDate);

        holder.mFab.setOnClickListener(view -> {
            Intent intent = new Intent(context, TermDetails.class);
            intent.putExtra(Constants.TERM_ID_KEY, term.getTermId());
            context.startActivity(intent);
        });

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.term_list_cv)
        CardView cv;
        @BindView(R.id.term_title)
        TextView mTermTitle;
        @BindView(R.id.term_start)
        TextView mTermStart;
        @BindView(R.id.term_end)
        TextView mTermEnd;
        @BindView(R.id.delete_term_fab)
        FloatingActionButton mFab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
