package com.example.c196studentscheduler.Term_Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.c196studentscheduler.R;
import com.example.c196studentscheduler.entity.Term;
import com.example.c196studentscheduler.viewmodel.TermViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditTerm extends AppCompatActivity {

    @BindView(R.id.et_edit_title)
    EditText editTitle;
    @BindView(R.id.et_edit_sDate)
    EditText editSDate;
    @BindView(R.id.et_edit_eDate)
    EditText editEDate;
    @BindView(R.id.edit_save_btn)
    Button saveBtn;
    @BindView(R.id.edit_cancel_btn)
    Button cancelBtn;

    private TermViewModel termViewModel;
    private boolean mNewTerm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);

        ButterKnife.bind(this);
        initViewModel();
    }

    private void initViewModel() {
        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);

        termViewModel.mLiveTerm.observe(this, new Observer<Term>() {
            @Override
            public void onChanged(Term term) {
                editTitle.setText(term.getName());
                editSDate.setText(term.getStartDate().toString());
                editEDate.setText(term.getEndDate().toString());
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle("New Term");
            mNewTerm = true;
        } else {
            setTitle("Edit Term");
            int termId = extras.getInt("term_id_key");
            termViewModel.loadData(termId);
        }
    }
}
