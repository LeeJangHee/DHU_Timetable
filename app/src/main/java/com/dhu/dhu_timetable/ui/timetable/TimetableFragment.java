package com.dhu.dhu_timetable.ui.timetable;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dhu.dhu_timetable.R;
import com.dhu.dhu_timetable.ui.main.MainActivityViewModel;
import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 시간표 오픈소스
 */
public class TimetableFragment extends Fragment {

    private TimetableView timetable;
    private ArrayList<Schedule> schedules = new ArrayList<>();
    private ArrayList<String> scheduleID = new ArrayList<>();
    private HashMap<Schedule, String> mGetID = new HashMap<>();

    private String user;

    int[] defaultHour = {0, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    // [75분용시간표현] A교시:09:00~10:15, B교시:10:30~11:45, C교시:14:00~15:15  D교시: 15:30~16:45
    // (index * 2 - 1, index * 2)
    int[] quarterMinute = {0, 0, 15, 30, 45, 0, 15, 30, 45};
    private Schedule schedule;
    private MainActivityViewModel mainActivityViewModel;

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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        timetable = (TimetableView) view.findViewById(R.id.timetable);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        // 레트로핏 --> 라이브데이터 가져오기
        mainActivityViewModel.getTimetable().observe(getViewLifecycleOwner(),
                new Observer<List<TimetableModel>>() {
                    @Override
                    public void onChanged(List<TimetableModel> timetableModels) {
                        // 새로 초기화 후
                        timetable.removeAll();
                        schedules.clear();
                        scheduleID.clear();
                        mGetID.clear();
                        setTimetable(timetableModels);
                        // 업데이트
                        if (!schedules.isEmpty()) {
                            for (int i = 0; i < schedules.size(); i++) {
                                ArrayList<Schedule> aSchedule = new ArrayList<>();
                                aSchedule.add(schedules.get(i));
                                timetable.add(aSchedule);
                                mGetID.put(schedules.get(i), scheduleID.get(i));
                            }
                        }
                    }
                });

        timetable.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                // 삭제서버 연결
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                Dialog dialog = builder.setMessage("삭제하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mainActivityViewModel.deleteTimetable(user, Integer.parseInt(mGetID.get(schedules.get(0))));
                                timetable.remove(idx);
                            }
                        })
                        .setNegativeButton("취소", null)
                        .create();
                dialog.show();
            }
        });
    }

    public void setTimetable(List<TimetableModel> timetableModels) {
        Time startTime;
        Time endTime;
        int index = 0;
        int cIndex = 0;
        // 타임 테이블 설정
        for (TimetableModel data : timetableModels) {
            schedule = new Schedule();
            String id = data.getId();
            String subjectName = data.getSubjectName();
            String workDay = data.getWorkDay();
            String cyber = data.getCyberCheck();
            String quarter = data.getQuarterCheck();

            // 사이버 강의일 경우
            if (cyber.equals("Y")) {
                schedule.setDay(6);
                schedule.setClassTitle(subjectName);
                schedule.setStartTime(new Time(cIndex+9, 0));
                schedule.setEndTime(new Time(cIndex+10, 0));
                cIndex++;
                schedules.add(index++, schedule);
                scheduleID.add(id);
                continue;
            }

            // 75분
            if (quarter.equals("Y")) {
                for (int i = 0; i < workDay.length(); i += 3) {
                    schedule = new Schedule();
                    int day = Character.getNumericValue(workDay.charAt(i)) - 1;
                    int hour = defaultHour[Character.getNumericValue(workDay.charAt(i + 1)) +
                            Character.getNumericValue(workDay.charAt(i + 2))];
                    int startMinute = quarterMinute[Character.getNumericValue(workDay.charAt(i + 2)) * 2 - 1];
                    int endMinute = quarterMinute[Character.getNumericValue(workDay.charAt(i + 2)) * 2];
                    schedule.setDay(day);
                    schedule.setClassTitle(subjectName);
                    schedule.setStartTime(new Time(hour, startMinute));
                    schedule.setEndTime(new Time(hour + 1, endMinute));

                    schedules.add(index++, schedule);
                    scheduleID.add(id);

                }
                continue;
            }

            // 초기값 저장
            String preTime = workDay.substring(1, 3);
            int preDay = Character.getNumericValue(workDay.charAt(0)) - 1;
            int preHour = defaultHour[Character.getNumericValue(preTime.charAt(0)) +
                    Character.getNumericValue(preTime.charAt(1))];
            int minute = 0;
            startTime = new Time(preHour, minute);
            endTime = new Time(preHour + 1, minute);

            // workDay --> 요일, 시간 분리하기
            // 시간 부분 --> time[0] + time[1]
            for (int i = 3; i < workDay.length(); i += 3) {
                int day = Character.getNumericValue(workDay.charAt(i)) - 1;
                int[] time = {Character.getNumericValue(workDay.charAt(i + 1)),
                        Character.getNumericValue(workDay.charAt(i + 2))};
                String preWorkDay = workDay.substring(i - 3, i);
                int[] workDayTime = {Character.getNumericValue(preWorkDay.charAt(1)),
                        Character.getNumericValue(preWorkDay.charAt(2))};
                int hour = defaultHour[time[0] + time[1]];

                // 날짜가 바뀌는 시점
                if (preDay != day) {
                    hour = defaultHour[workDayTime[0] + workDayTime[1]];
                    endTime.setHour(hour + 1);
                    endTime.setMinute(minute);

                    schedule.setDay(preDay);
                    schedule.setClassTitle(subjectName);
                    schedule.setStartTime(startTime);
                    schedule.setEndTime(endTime);

                    schedules.add(index++, schedule);
                    continue;
                }
                endTime.setHour(hour + 1);

                schedule.setDay(day);
                schedule.setClassTitle(subjectName);
                schedule.setStartTime(startTime);
                schedule.setEndTime(endTime);
            }
            schedules.add(index++, schedule);
            scheduleID.add(id);
        }
    }
}
