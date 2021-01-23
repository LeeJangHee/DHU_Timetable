package com.dhu.dhu_timetable.ui.subject;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dhu.dhu_timetable.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class SubjectAdapter extends RecyclerView.ViewHolder {

    protected TextView subject_year;
    protected TextView subject_semester;
    protected TextView subject_name;
    protected TextView subject_major;
    protected TextView subject_score;
    protected TextView subject_day_time;
    protected TextView subject_professor;
    protected TextView expandable_classroom;
    protected TextView expandable_level;
    protected TextView expandable_finish_check;
    protected TextView expandable_cyber;
    protected MaterialCardView materialCardView;
    protected ConstraintLayout constraintLayout;
    protected ImageButton imageBtn;
    protected MaterialButton btn_ok;

    // Click Listener
    OnSubjectListener onSubjectListener;

    public SubjectAdapter(@NonNull View itemView, OnSubjectListener onSubjectListener) {
        super(itemView);
        this.onSubjectListener = onSubjectListener;

        subject_year = (TextView) itemView.findViewById(R.id.subject_year);
        subject_semester = (TextView) itemView.findViewById(R.id.subject_semester);
        subject_name = (TextView) itemView.findViewById(R.id.subject_name);
        subject_major = (TextView) itemView.findViewById(R.id.subject_major);
        subject_score = (TextView) itemView.findViewById(R.id.subject_score);
        subject_day_time = (TextView) itemView.findViewById(R.id.subject_day_time);
        subject_professor = (TextView) itemView.findViewById(R.id.subject_professor);
        expandable_classroom = (TextView) itemView.findViewById(R.id.expandable_classroom);
        expandable_level = (TextView) itemView.findViewById(R.id.expandable_level);
        expandable_finish_check = (TextView) itemView.findViewById(R.id.expandable_finish_check);
        expandable_cyber = (TextView) itemView.findViewById(R.id.expandable_cyber);
        materialCardView = (MaterialCardView) itemView.findViewById(R.id.cardView);
        constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.expandable_view);
        imageBtn = (ImageButton) itemView.findViewById(R.id.image_btn);
        btn_ok = (MaterialButton) itemView.findViewById(R.id.subject_item_btn_ok);
    }


}
