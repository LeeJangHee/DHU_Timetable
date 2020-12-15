package com.example.dhu_timetable.ui.subject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.dhu_timetable.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.MyViewholder> {

    private Context context;
    private List<SubjectModel> subjectModels;

    public SubjectAdapter(Context context, List<SubjectModel> subjectModels) {
        this.context = context;
        this.subjectModels = subjectModels;
    }

    public void setSubjectList(List<SubjectModel> subjectModels) {
        this.subjectModels = subjectModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_test_item, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        holder.textView.setText(this.subjectModels.get(position).getText());
        holder.imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoTransition transition = new AutoTransition();

                if (holder.constraintLayout.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(holder.materialCardView, transition);
                    holder.imageBtn.setImageResource(R.drawable.ic_baseline_expand_less_24);
                    holder.constraintLayout.setVisibility(View.VISIBLE);
                } else {
                    holder.imageBtn.setImageResource(R.drawable.ic_baseline_expand_more_24);
                    holder.constraintLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.subjectModels.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        TextView textView;
        MaterialCardView materialCardView;
        ConstraintLayout constraintLayout;
        ImageButton imageBtn;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.test);
            materialCardView = (MaterialCardView) itemView.findViewById(R.id.cardView);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.expandable_view);
            imageBtn = (ImageButton) itemView.findViewById(R.id.image_btn);
        }
    }


}
