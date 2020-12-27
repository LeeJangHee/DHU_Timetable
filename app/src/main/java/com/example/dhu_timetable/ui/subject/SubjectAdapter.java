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

    public SubjectAdapter(Context context) {
        this.context = context;
    }

    public void setSubjectList(List<SubjectModel> subjectModels) {
        this.subjectModels = subjectModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_subject_item, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        // 데이터 모델 초기화
        // 기본데이터 : 년도, 학기, 과목명, 학과, 학점, 요일, 시간, 교수
        holder.subject_year.setText(this.subjectModels.get(position).getYear()+"");
        holder.subject_semester.setText(this.subjectModels.get(position).getSemester() / 10+"학기");
        holder.subject_name.setText(this.subjectModels.get(position).getSubjectName()+"");
        holder.subject_major.setText(this.subjectModels.get(position).getMajorName()+"");
        holder.subject_score.setText(this.subjectModels.get(position).getScore()+"학점");
        holder.subject_day_time.setText(this.subjectModels.get(position).getPublishDay()+"");
        holder.subject_professor.setText(this.subjectModels.get(position).getProfessor()+"");

        // 확장데이터 : 강의실, 학년, 사이버강의


        // 카드뷰 클릭시 이벤트 발생
        holder.imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 리사이클러뷰 아이템 -> 시간표로 저장
                AutoTransition transition = new AutoTransition();
                if (holder.constraintLayout.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(holder.materialCardView, transition);
                    holder.imageBtn.setImageResource(R.drawable.ic_baseline_expand_less_24);
                    holder.constraintLayout.setVisibility(View.VISIBLE);
                    holder.subject_name.setSelected(true);
                } else {
                    holder.imageBtn.setImageResource(R.drawable.ic_baseline_expand_more_24);
                    holder.constraintLayout.setVisibility(View.GONE);
                    holder.subject_name.setSelected(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (this.subjectModels != null)
            return this.subjectModels.size();
        return 0;
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        TextView subject_year;
        TextView subject_semester;
        TextView subject_name;
        TextView subject_major;
        TextView subject_score;
        TextView subject_day_time;
        TextView subject_professor;
        MaterialCardView materialCardView;
        ConstraintLayout constraintLayout;
        ImageButton imageBtn;

        /**
         * 리사이클러뷰 아이템 뷰 부분
         * @param itemView = 각각의 리사이클러 아이템 뷰
         */
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            subject_year = (TextView) itemView.findViewById(R.id.subject_year);
            subject_semester = (TextView) itemView.findViewById(R.id.subject_semester);
            subject_name = (TextView) itemView.findViewById(R.id.subject_name);
            subject_major = (TextView) itemView.findViewById(R.id.subject_major);
            subject_score = (TextView) itemView.findViewById(R.id.subject_score);
            subject_day_time = (TextView) itemView.findViewById(R.id.subject_day_time);
            subject_professor = (TextView) itemView.findViewById(R.id.subject_professor);
            materialCardView = (MaterialCardView) itemView.findViewById(R.id.cardView);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.expandable_view);
            imageBtn = (ImageButton) itemView.findViewById(R.id.image_btn);

        }
    }


}
