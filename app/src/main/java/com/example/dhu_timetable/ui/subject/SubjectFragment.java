package com.example.dhu_timetable.ui.subject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhu_timetable.R;
import com.example.dhu_timetable.network.SubjectApiClient;
import com.example.dhu_timetable.ui.main.MainActivityViewModel;
import com.example.dhu_timetable.ui.timetable.TimetableModel;

import java.util.ArrayList;
import java.util.List;


/**
 * 수강정보 UI로직 구현
 */
public class SubjectFragment extends Fragment implements OnSubjectListener {
    private static final String TAG = "janghee";

    private static final String NOW_YEAR = "YEAR";
    private static final String NOW_MONTH = "MONTH";
    private static final String NOW_USER = "USER";
    private static final int REQUEST_CODE = 100;

    private List<SubjectModel> subjectList = new ArrayList<>();
    private List<TimetableModel> timetableList = new ArrayList<>();
    private String user;
    private RecyclerView recyclerView;

    private MainActivityViewModel mainActivityViewModel;
    private SubjectRecyclerView subjectRecyclerView;

    // 필요하면 사용하기 위한 newInstance
    public static SubjectFragment newInstance(String year, String month, String user) {
        SubjectFragment fragment = new SubjectFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NOW_YEAR, year);
        bundle.putString(NOW_MONTH, month);
        bundle.putString(NOW_USER, user);

        fragment.setArguments(bundle);
        return fragment;
    }

    // SearchActivity로 부터 데이터 전달받음 --> 현재 미사용
    public static SubjectFragment searchInstance(String year, String semester, String subjectName, String level, String major, String cyber) {
        SubjectFragment fragment = new SubjectFragment();
        Bundle bundle = new Bundle();
        bundle.putString("year", year);
        bundle.putString("semester", semester);
        bundle.putString("subjectName", subjectName);
        bundle.putString("level", level);
        bundle.putString("major", major);
        bundle.putString("cyber", cyber);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getArguments().getString(NOW_USER);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject_list, container, false);
        // 리사이클러뷰
        recyclerView = view.findViewById(R.id.subject_recyclerview);
        // 리사이클러뷰 초기화 + 뷰모델
        ConfigureRecyclerView();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        // 뷰모델 + 라이브데이터
        mainActivityViewModel.getSubjectData().observe(requireActivity(), new Observer<List<SubjectModel>>() {
            @Override
            public void onChanged(List<SubjectModel> subjectModels) {
                subjectRecyclerView.setSubjectList(subjectModels);
            }
        });

    }

    private void ConfigureRecyclerView() {
        subjectRecyclerView = new SubjectRecyclerView(user, this);

        recyclerView.setAdapter(subjectRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onAddSubject(String email, String subjectName, String workDay, String cyber, String quarter) {
        mainActivityViewModel.insertTimetable(email, subjectName, workDay, cyber, quarter);
    }

    /**
     * 나의 강의 시간표에 겹치는 시간이 있는지 확인
     * @param workDay : 넣고 싶은 강의의 시간
     * @return 넣을수 있는지 없는지 반환
     */
    @Override
    public boolean onTimeCheck(String workDay) {
        // TODO: 뷰모델을 가져올 수 없음 NullPointerException = 수정 필요
        try {
            timetableList = mainActivityViewModel.getTimetableList();
            for (TimetableModel t : timetableList) {
                for (int i = 0; i < t.getWorkDay().length(); i += 3) {
                    if (workDay.contains(t.getWorkDay().substring(i, i + 3))) {
                        // 있음
                        Log.d(TAG, "timeCheck: false");
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            Log.v(TAG, "타임체크 오류: "+e.getMessage());
            return false;
        }
        Log.d(TAG, "timeCheck: true");
        return true;
    }

    @Override
    public void onNextAddSubject(String email) {
        mainActivityViewModel.nextInsertTimetable(email);
    }
}
