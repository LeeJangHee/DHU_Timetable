package com.dhu.dhu_timetable.ui.subject;


/**
 * 리사이클러뷰 아이템의 ViewModel 연결 인터페이스
 */
public interface OnSubjectListener {

    void onAddSubject(String email, String subjectName, String workDay, String cyber, String quarter);

    boolean onTimeCheck(String workDay);
}
