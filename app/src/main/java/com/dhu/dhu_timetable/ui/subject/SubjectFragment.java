package com.dhu.dhu_timetable.ui.subject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dhu.dhu_timetable.databinding.FragmentSubjectListBinding;
import com.dhu.dhu_timetable.model.SubjectModel;
import com.dhu.dhu_timetable.model.TimetableModel;
import com.dhu.dhu_timetable.ui.main.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.dhu.dhu_timetable.util.Conts.*;


/**
 * 수강정보 UI로직 구현
 */
public class SubjectFragment extends Fragment implements OnSubjectListener {
    private static final String TAG = "janghee";

    private static final String NOW_YEAR = "YEAR";
    private static final String NOW_MONTH = "MONTH";
    private static final String NOW_USER = "USER";

    private FragmentSubjectListBinding binding;

    private List<TimetableModel> timetableList = new ArrayList<>();
    private String user;

    private MainActivityViewModel mainActivityViewModel;
    private SubjectRecyclerAdapter subjectRecyclerAdapterView;

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
        bundle.putString(YEAR, year);
        bundle.putString(SEMESTER, semester);
        bundle.putString(SUBJECT_NAME, subjectName);
        bundle.putString(LEVEL, level);
        bundle.putString(MAJOR, major);
        bundle.putString(CYBER, cyber);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = requireArguments().getString(NOW_USER);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSubjectListBinding.inflate(inflater, container, false);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        binding.setLifecycleOwner(requireActivity());
        // 리사이클러뷰 초기화 + 뷰모델
        configureRecyclerView();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setMainViewModel(mainActivityViewModel);
        // 뷰모델 + 라이브데이터
        mainActivityViewModel.getSubjectData().observe(requireActivity(), subjectModels -> subjectRecyclerAdapterView.setSubjectList(subjectModels));

    }

    private void configureRecyclerView() {
        subjectRecyclerAdapterView = new SubjectRecyclerAdapter(requireActivity(), user, this);

        binding.subjectRecyclerview.setAdapter(subjectRecyclerAdapterView);
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
            timetableList = mainActivityViewModel.getTimetable().getValue();
            if (timetableList == null) return false;
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
}
