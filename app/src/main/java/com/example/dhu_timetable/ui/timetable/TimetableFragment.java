package com.example.dhu_timetable.ui.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.dhu_timetable.R;
import com.example.dhu_timetable.ui.subject.SubjectFragment;
import com.example.dhu_timetable.ui.subject.SubjectViewModel;
import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;

import java.util.ArrayList;
import java.util.List;

/**
 * 시간표 오픈소스
 */
public class TimetableFragment extends Fragment {

    private TimetableView timetable;
    private TimetableViewModel viewModel;
    private ArrayList<Schedule> schedule = new ArrayList<>();

    private String user;

    public static TimetableFragment newInstance(String user) {
        TimetableFragment fragment = new TimetableFragment();
        Bundle bundle = new Bundle();
        bundle.putString("user", user);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getArguments().getString("user");
        viewModel = new ViewModelProvider(this).get(TimetableViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        timetable = (TimetableView) view.findViewById(R.id.timetable);

        viewModel.init(user);
        viewModel.getTimetableData().observe(getViewLifecycleOwner(), new Observer<List<TimetableModel>>() {
            @Override
            public void onChanged(List<TimetableModel> timetableModels) {
                // TODO: 타임테이블 데이터 가져오기

            }
        });

        timetable.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                // TODO: 수강 취소 이벤트
            }
        });

        return view;
    }
}
