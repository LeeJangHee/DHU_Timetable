package com.example.dhu_timetable.ui.subject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhu_timetable.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 수강정보 UI로직 구현
 */
public class SubjectFragment extends Fragment {

    private static final String NOW_YEAR = "YEAR";
    private static final String NOW_MONTH = "MONTH";

    private List<SubjectModel> subjectList = new ArrayList<>();
    private SubjectAdapter adapter;
    private SubjectViewModel viewModel;

    // 필요하면 사용하기 위한 newInstance
    public static SubjectFragment newInstance(String year, String month) {
        SubjectFragment fragment = new SubjectFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NOW_YEAR, year);
        bundle.putString(NOW_MONTH, month);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SubjectViewModel.class);
        viewModel.init();   // 테스트 용
        // 실제 사용
//        viewModel.init(getArguments().getString(NOW_YEAR),
//                getArguments().getString(NOW_MONTH));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject_list, container, false);

        // 리사이클러뷰
        RecyclerView recyclerView = view.findViewById(R.id.subject_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SubjectAdapter(getContext());
        recyclerView.setAdapter(adapter);

        // 뷰모델 + 라이브데이터
        viewModel.getSubjectData().observe(getViewLifecycleOwner(), new Observer<List<SubjectModel>>() {
            @Override
            public void onChanged(List<SubjectModel> subjectModels) {
                if (subjectList != null) {
                    subjectList = subjectModels;
                    adapter.setSubjectList(subjectModels);
                }
            }
        });

        return view;
    }

}
