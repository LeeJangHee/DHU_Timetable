package com.example.dhu_timetable.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dhu_timetable.ui.subject.SubjectFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStateAdapter {
    private static int TAB_ITEM_COUNT;

    /**
     * 페이지 어뎁터 불러올 때
     * @param fa = 불러올 프레그먼트
     * @param size = 탭 개수
     */
    public SectionsPagerAdapter(FragmentActivity fa, int size) {
        super(fa);
        TAB_ITEM_COUNT = size;
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
                return new SubjectFragment();
            default:
                return PlaceholderFragment.newInstance(position + 1);
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