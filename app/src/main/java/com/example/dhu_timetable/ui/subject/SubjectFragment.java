package com.example.dhu_timetable.ui.subject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhu_timetable.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubjectFragment extends Fragment {

    private SubjectAdapter adapter;
    private View subjectView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        subjectView = (View) inflater.inflate(R.layout.fragment_subject_list, container, false);

        init();
        getData(40);

        return subjectView;
    }

    /**
     * 리사이클러뷰를 뷰에 넣기
     */
    private void init() {
        RecyclerView recyclerView = subjectView.findViewById(R.id.subject_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new SubjectAdapter();
        recyclerView.setAdapter(adapter);
    }

    /**
     * 리사이클러뷰 데이터 초기화
     */
    private void getData(int size) {
        List<String> data = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            SubjectItem subjectItem = new SubjectItem();

            subjectItem.setText("테스트 " + i);

            adapter.addItem(subjectItem);
        }
        adapter.notifyDataSetChanged();
    }
}
