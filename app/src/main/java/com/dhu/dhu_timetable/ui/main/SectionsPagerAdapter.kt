package com.dhu.dhu_timetable.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dhu.dhu_timetable.ui.subject.SubjectFragment.Companion.newInstance
import com.dhu.dhu_timetable.ui.timetable.TimetableFragment.Companion.newInstance

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(
    fa: FragmentActivity,
    private val tabItemCount: Int,
    private val year: String,
    private val month: String,
    private val currentUser: String,
    private val onUpdateListener: OnUpdateListener
) : FragmentStateAdapter(fa) {

    /**
     * 텝 레이아웃에 맞는 프레그먼트 구해주는 함수
     * @param position = Tab count
     * @return 탭에 맞는 프레그먼트 리턴
     */
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> newInstance(year, month, currentUser)
            else -> {
                onUpdateListener.OnUpdateTimetable(currentUser)
                newInstance(currentUser)
            }
        }
    }

    /**
     * TabLayout 개수 구하는 함수
     * @return Tab 개수
     */
    override fun getItemCount(): Int {
        return tabItemCount
    }
}