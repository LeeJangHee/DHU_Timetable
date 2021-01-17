package com.example.dhu_timetable.ui.timetable;

import android.os.Bundle;
import android.util.Log;
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

    private String TAG = "janghee";

    private TimetableView timetable;
    private TimetableViewModel viewModel;
    private ArrayList<Schedule> schedules = new ArrayList<>();

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
                if (!schedules.isEmpty()) {
                    setTimetable(timetableModels);
                    timetable.add(schedules);
                }
                Log.d(TAG, "Timetable VM onChanged: ");
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

    public void setTimetable(List<TimetableModel> timetableModels) {
        Schedule schedule = new Schedule();
        Time startTime;
        Time endTime;
        int[] defultDay = {0, 9, 10, 11, 12, 13, 14, 15, 16, 17};
        // [75분용시간표현] A교시:09:00~10:15, B교시:10:30~11:45, C교시:14:00~15:15  D교시: 15:30~16:45
        int[] quarterMinute = {0, 0, 15, 30, 45, 0, 15, 30, 45};
        // 타임 테이블 설정
        for (TimetableModel data : timetableModels) {
            String id = data.getId();
            String subjectName = data.getSubjectName();
            String workDay = data.getWorkDay();
            String cyber = data.getCyberCheck();
            String quarter = data.getQuarterCheck();

            // 사이버 강의일 경우
            if (!cyber.isEmpty()) {
                Log.d(TAG, "cyber ok");
                continue;
            }

            // 75분
            if (!quarter.isEmpty()) {
                Log.d(TAG, "quarter ok");
                for (int i = 0; i < workDay.length(); i += 3) {
                    String time = workDay.substring(i + 1, i + 3);
                    int day = workDay.charAt(i) - '0';
                    int hour = defultDay[time.charAt(i + 1) - '0'];
                    int minute = quarterMinute[time.charAt(i + 1) - '0'];
                    schedule.setDay(day - 1);
                    schedule.setClassTitle(subjectName);
                    schedule.setStartTime(new Time(hour, minute));
                    schedule.setEndTime(new Time(hour + 1, minute + 1));

                    schedules.add(schedule);
                }

                continue;
            }

            // 초기값 저장
            String preTime = workDay.substring(1, 3);
            int preDay = workDay.charAt(0) - '0';
            int preHour = defultDay[preTime.charAt(1) - '0'];
            int preMinute = 0;
            startTime = new Time(preHour, preMinute);
            endTime = new Time(preHour + 1, preMinute + 1);

            // workDay --> 요일, 시간 분리하기
            for (int i = 0; i < workDay.length(); i += 3) {
                int day = workDay.charAt(i) - '0';
                String time = workDay.substring(i + 1, i + 3);
                int hour = time.charAt(1) - '0';
                int minute = 0;

                // 날짜가 바뀌는 시점
                if (preDay != day) {
                    hour = defultDay[workDay.substring(i - 2, i).charAt(1) - '0'];
                    endTime.setHour(hour);
                    endTime.setMinute(minute);

                    schedule.setDay(day - 1);
                    schedule.setClassTitle(subjectName);
                    schedule.setStartTime(startTime);
                    schedule.setEndTime(endTime);

                    schedules.add(schedule);
                    continue;
                }

                schedule.setDay(day - 1);
                schedule.setClassTitle(subjectName);
                schedule.setStartTime(startTime);
                schedule.setEndTime(endTime);

            }
            schedules.add(schedule);
        }
    }
}
