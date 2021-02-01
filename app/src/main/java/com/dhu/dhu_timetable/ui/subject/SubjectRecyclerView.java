package com.dhu.dhu_timetable.ui.subject;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.dhu.dhu_timetable.R;

import java.util.List;

public class SubjectRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "janghee";
    private String user;
    private List<SubjectModel> subjectModels;
    private boolean[] isTime = new boolean[2000];

    private OnSubjectListener onSubjectListener;

    public SubjectRecyclerView(String user, OnSubjectListener onSubjectListener) {
        this.user = user;
        this.onSubjectListener = onSubjectListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_subject_item, parent, false);
        return new SubjectAdapter(view, onSubjectListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // 데이터 모델 초기화
        // 기본데이터 : 년도, 학기, 과목명, 학과, 학점, 요일, 시간, 교수
        SubjectModel models = subjectModels.get(position);

        ((SubjectAdapter)holder).subject_year.setText(models.getYear() + "");
        ((SubjectAdapter)holder).subject_semester.setText(models.getSemester() / 10 + "학기");
        ((SubjectAdapter)holder).subject_name.setText(models.getSubjectName() + "");
        ((SubjectAdapter)holder).subject_major.setText(models.getMajorName() + "");
        ((SubjectAdapter)holder).subject_score.setText(models.getScore() + "학점");
        ((SubjectAdapter)holder).subject_day_time.setText(models.getPublishDay().replace(" ", "") + "");
        if (models.getProfessor().isEmpty()) {
            ((SubjectAdapter)holder).subject_professor.setText("미정");
        } else {
            ((SubjectAdapter)holder).subject_professor.setText(models.getProfessor() + "");
        }

        // 확장데이터 : 강의실, 학년, 사이버강의, 이수구분
        if (models.getClassroom().isEmpty()) {
            ((SubjectAdapter)holder).expandable_classroom.setText("강의실 준비중 입니다.");
        } else {
            ((SubjectAdapter)holder).expandable_classroom.setText("강의실: " + models.getClassroom());
        }
        ((SubjectAdapter)holder).expandable_level.setText("학년: " + models.getLevel() + " 학년");
        ((SubjectAdapter)holder).expandable_finish_check.setText("이수구분: " + models.getFinishCheck());
        if (models.getCyberCheck().equals("Y")) {
            ((SubjectAdapter)holder).expandable_cyber.setText("사이버 강의: YES");
            ((SubjectAdapter)holder).expandable_cyber.setVisibility(View.VISIBLE);
            ((SubjectAdapter)holder).expandable_classroom.setVisibility(View.GONE);
        } else {
            ((SubjectAdapter)holder).expandable_cyber.setVisibility(View.GONE);
            ((SubjectAdapter)holder).expandable_classroom.setVisibility(View.VISIBLE);
        }

        // 카드뷰 클릭시 이벤트 발생
        ((SubjectAdapter)holder).imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubjectListener.onNextAddSubject(user);
                AutoTransition transition = new AutoTransition();
                if (((SubjectAdapter)holder).constraintLayout.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(((SubjectAdapter)holder).materialCardView, transition);
                    ((SubjectAdapter)holder).imageBtn.setImageResource(R.drawable.ic_baseline_expand_less_24);
                    ((SubjectAdapter)holder).constraintLayout.setVisibility(View.VISIBLE);
                    ((SubjectAdapter)holder).subject_name.setSelected(true);
                    isTime[position] = onSubjectListener.onTimeCheck(models.getWorkDay());
                    if (!isTime[position]){
                        ((SubjectAdapter)holder).btn_ok.setEnabled(false);
                    } else {
                        ((SubjectAdapter)holder).btn_ok.setEnabled(true);
                    }
                } else {
                    ((SubjectAdapter)holder).imageBtn.setImageResource(R.drawable.ic_baseline_expand_more_24);
                    ((SubjectAdapter)holder).constraintLayout.setVisibility(View.GONE);
                    ((SubjectAdapter)holder).subject_name.setSelected(false);
                }
            }
        });

        // 담기 버튼 클릭
        ((SubjectAdapter)holder).btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (models.getWorkDay().isEmpty() && models.getCyberCheck().equals("")) {
                    Toast.makeText(holder.itemView.getContext(), "시간을 확인중입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // TODO: 과목 데이터 -> 시간표로 저장
                // 시간 데이터 예외가 많음
                // 여러가지 요일, 시간 존재
                // [75분용시간표현] A교시:09:00~10:15, B교시:10:30~11:45, C교시:14:00~15:15  D교시: 15:30~16:45
                if (isTime[position]) {
                    // 시간표 넣기 가능
                    Toast.makeText(holder.itemView.getContext(), "시간표 성공", Toast.LENGTH_SHORT).show();
                    // insert into timetable
                    onSubjectListener.onAddSubject(
                            user,
                            models.subjectName,
                            models.workDay,
                            models.cyberCheck,
                            models.quarterCheck
                    );
                    onSubjectListener.onNextAddSubject(user);
                    ((SubjectAdapter)holder).btn_ok.setEnabled(false);
                } else {
                    // 불가능
                    Toast.makeText(holder.itemView.getContext(), "원하는 시간에 강의가 있습니다.", Toast.LENGTH_SHORT).show();
                }
                isTime[position] = onSubjectListener.onTimeCheck(models.workDay);
                Log.d(TAG, "담기 후: "+isTime[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (subjectModels != null) {
            return subjectModels.size();
        }
        return 0;
    }

    public void setSubjectList(List<SubjectModel> subjectModels) {
        this.subjectModels = subjectModels;
        notifyDataSetChanged();
    }
}
