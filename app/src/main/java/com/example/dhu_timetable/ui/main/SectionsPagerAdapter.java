package com.example.dhu_timetable.ui.main;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dhu_timetable.ui.subject.SubjectFragment;
import com.example.dhu_timetable.ui.timetable.TimetableFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStateAdapter {
    private static int TAB_ITEM_COUNT;
    private String year;    // 현재 년도
    private String month;   // 현재 월
    private String currentUser; // 현재 유저
    private OnUpdateListener onUpdateListener;

    /**
     * 페이지 어뎁터 불러올 때
     * @param fa = 불러올 프레그먼트
     * @param size = 탭 개수
     */
    public SectionsPagerAdapter(FragmentActivity fa, int size, String year, String month, String currentUser, OnUpdateListener onUpdateListener) {
        super(fa);
        TAB_ITEM_COUNT = size;
        this.year = year;
        this.month = month;
        this.currentUser = currentUser;
        this.onUpdateListener = onUpdateListener;
    }

    /**
     * 텝 레이아웃에 맞는 프레그먼트 구해주는 함수
     * @param position = Tab count
     * @return 탭에 맞는 프레그먼트 리턴
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return SubjectFragment.newInstance(year, month, currentUser);
            default:
                onUpdateListener.OnUpdateTimetable(currentUser);
                return TimetableFragment.newInstance(currentUser);
        }

    }

    /**
     * TabLayout 개수 구하는 함수
     * @return Tab 개수
     */
    @Override
    public int getItemCount() {
        return TAB_ITEM_COUNT;
    }
}