package com.example.dhu_timetable.ui.subject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.dhu_timetable.R;
import com.google.android.material.button.MaterialButton;
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
        SubjectModel models = this.subjectModels.get(position);
        holder.subject_year.setText(models.getYear() + "");
        holder.subject_semester.setText(models.getSemester() / 10 + "학기");
        holder.subject_name.setText(models.getSubjectName() + "");
        holder.subject_major.setText(models.getMajorName() + "");
        holder.subject_score.setText(models.getScore() + "학점");
        holder.subject_day_time.setText(models.getPublishDay() + "");
        if (models.getProfessor().isEmpty()){
            holder.subject_professor.setText("미정");
        } else {
            holder.subject_professor.setText(models.getProfessor() + "");
        }

        // 확장데이터 : 강의실, 학년, 사이버강의, 이수구분
        if (models.getClassroom().isEmpty()) {
            holder.expandable_classroom.setText("강의실 준비중 입니다.");
        } else {
            holder.expandable_classroom.setText("강의실: " + models.getClassroom());
        }
        holder.expandable_level.setText("학년: " + models.getLevel() + " 학년");
        holder.expandable_finish_check.setText("이수구분: " + models.getFinishCheck());
        if (models.getCyberCheck().equals("Y")) {
            holder.expandable_cyber.setText("사이버 강의: YES");
            holder.expandable_classroom.setVisibility(View.GONE);
        } else {
            holder.expandable_cyber.setVisibility(View.GONE);
        }

        // 카드뷰 클릭시 이벤트 발생
        holder.imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        // 담기 버튼 클릭
        holder.btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 과목 데이터 -> 시간표로 저장
                Toast.makeText(context, models.getSubjectCode(), Toast.LENGTH_SHORT).show();
                // 시간 데이터 예외가 많음
                // 여러가지 요일, 시간 존재
                // 쿼터 시간 존재
                //[75분용시간표현] A교시:09:00~10:15, B교시:10:30~11:45, C교시:14:00~15:15  D교시: 15:30~16:45
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

        private final TextView subject_year;
        private final TextView subject_semester;
        private final TextView subject_name;
        private final TextView subject_major;
        private final TextView subject_score;
        private final TextView subject_day_time;
        private final TextView subject_professor;
        private final TextView expandable_classroom;
        private final TextView expandable_level;
        private final TextView expandable_finish_check;
        private final TextView expandable_cyber;
        private final MaterialCardView materialCardView;
        private final ConstraintLayout constraintLayout;
        private final ImageButton imageBtn;
        private final MaterialButton btn_ok;

        /**
         * 리사이클러뷰 아이템 뷰 부분
         *
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


}
