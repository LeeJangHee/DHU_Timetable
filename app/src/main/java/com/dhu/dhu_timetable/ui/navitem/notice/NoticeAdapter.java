package com.dhu.dhu_timetable.ui.navitem.notice;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dhu.dhu_timetable.R;
import com.dhu.dhu_timetable.model.NoticeModel;
import com.google.android.material.card.MaterialCardView;

import java.util.List;


public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {
    private Context context;
    private NoticeViewModel noticeViewModel;
    private List<NoticeModel> noticeModels;

    public NoticeAdapter(Context context, NoticeViewModel noticeViewModel) {
        this.context = context;
        this.noticeViewModel = noticeViewModel;
    }

    public void setNoticeList(List<NoticeModel> noticeModels) {
        this.noticeModels = noticeModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_notice_item, parent, false);
        return new NoticeAdapter.NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        NoticeModel noticeModel = this.noticeModels.get(position);

        // 미확장데이터
        //holder.notice_id.setText(noticeModel.getId() + ".");
        holder.notice_title.setText(noticeModel.getTitle() + "");
        holder.notice_date.setText(noticeModel.getDate() + "");

        // 확장데이터
        holder.notice_content.setText(noticeModel.getContent() + "");

        Log.v("jaemin","" + noticeModel.getId() + noticeModel.getTitle() + noticeModel.getContent() + noticeModel.getDate());
        holder.notice_expBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                if(holder.constraintLayout.getVisibility() == View.GONE){
                    TransitionManager.beginDelayedTransition(holder.materialCardView, autoTransition);
                    holder.notice_expBtn.setImageResource(R.drawable.ic_baseline_expand_less_24);
                    holder.constraintLayout.setVisibility(View.VISIBLE);
                    holder.notice_title.setSelected(true);
                }
                else{
                    holder.notice_expBtn.setImageResource(R.drawable.ic_baseline_expand_more_24);
                    holder.constraintLayout.setVisibility(View.GONE);
                    holder.notice_title.setSelected(false);
                }
            }
        });



    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder {
        //private final TextView notice_id;
        private final TextView notice_title;
        private final TextView notice_content;
        private final TextView notice_date;
        private final ImageButton notice_expBtn;
        private final ConstraintLayout constraintLayout;
        private final MaterialCardView materialCardView;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            //notice_id = (TextView) itemView.findViewById(R.id.notice_id);
            notice_title = (TextView) itemView.findViewById(R.id.notice_title);
            notice_content = (TextView) itemView.findViewById(R.id.notice_expandable_content);
            notice_date = (TextView) itemView.findViewById(R.id.notice_date);
            notice_expBtn = (ImageButton) itemView.findViewById(R.id.notice_expbtn);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.notice_expandable_view);
            materialCardView = (MaterialCardView) itemView.findViewById(R.id.notice_cardView);
        }
    }


    @Override
    public int getItemCount() {
        if (this.noticeModels != null) {
            return this.noticeModels.size();
        }
        return 0;
    }

}
