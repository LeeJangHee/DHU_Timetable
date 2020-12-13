package com.example.dhu_timetable.ui.subject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhu_timetable.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ItemViewHolder> {

    // 어뎁터에 들어갈 리스트
    private final ArrayList<SubjectItem> subjectItemArrayList = new ArrayList<>();

    @NonNull
    @Override
    public SubjectAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_subject_item, parent, false);
        return new ItemViewHolder(view);
    }

    /**
     * 아이템 재사용을 위한 함수
     * @param holder = 뷰 어뎁터
     * @param position = 어뎁터아이템 위치
     */
    @Override
    public void onBindViewHolder(@NonNull SubjectAdapter.ItemViewHolder holder, int position) {
        holder.onBind(subjectItemArrayList.get(position));
    }

    /**
     * 리사이클러뷰 크기 초기화
     * @return = 아이템의 크기
     */
    @Override
    public int getItemCount() {
        return subjectItemArrayList.size();
    }

    /**
     * 리스트에 아이템 넣기
     * @param subjectItem = 불러올 아이템
     */
    public void addItem(SubjectItem subjectItem) {
        subjectItemArrayList.add(subjectItem);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView test;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            test = (TextView) itemView.findViewById(R.id.subject_item);
        }

        public void onBind(@NotNull SubjectItem subjectItem) {
            test.setText(subjectItem.getText());
        }
    }

}
