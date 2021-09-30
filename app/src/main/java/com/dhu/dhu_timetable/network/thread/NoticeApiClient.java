package com.dhu.dhu_timetable.network.thread;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dhu.dhu_timetable.model.NoticeModel;
import com.dhu.dhu_timetable.network.APIService;
import com.dhu.dhu_timetable.network.RetrofitConnect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class NoticeApiClient {
    private MutableLiveData<List<NoticeModel>> mNoticeModels;
    private RetrieveNoticeRunnable retrieveNoticeRunnable;

    private static NoticeApiClient instance;

    public static NoticeApiClient getInstance(){
        if(instance == null){
            instance = new NoticeApiClient();
        }
        return instance;
    }

    public NoticeApiClient(){ mNoticeModels = new MutableLiveData<>(); }

    public MutableLiveData<List<NoticeModel>> getNoticeData() { return mNoticeModels; }

    public void setNoticeData() {
        if(retrieveNoticeRunnable != null){
            retrieveNoticeRunnable = null;
        }

        retrieveNoticeRunnable = new NoticeApiClient.RetrieveNoticeRunnable();

        final Future mHandler = AppExecutors.getInstance()
                .networkIO()
                .submit(retrieveNoticeRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable(){
            @Override
            public void run() {
                mHandler.cancel(true);
            }
        }, 5, TimeUnit.SECONDS);

    }

    private class RetrieveNoticeRunnable implements Runnable {
        boolean cancelRequest;

        public RetrieveNoticeRunnable(){
            cancelRequest = false;
        }

        @Override
        public void run() {
            try{
                Response<List<NoticeModel>> response = getNotice().execute();

                if(cancelRequest)
                    return;

                if(response.code() == 200){
                    List<NoticeModel> noticeModels = new ArrayList<>(response.body());
                    mNoticeModels.postValue(noticeModels);
                }
                else{
                    String error = response.errorBody().string();
                    Log.v("jaemin", "getNotice background error: " + error);
                }

            } catch (IOException e){
                e.printStackTrace();
                mNoticeModels.postValue(null);
            }
        }

        private Call<List<NoticeModel>> getNotice(){
            return RetrofitConnect.INSTANCE.getRetrofit()
                    .create(APIService.class)
                    .getNotice();
        }

        private void cancelRequest(){
            Log.v("jaemin", "Cancelling Request getNotice");
            cancelRequest = true;
        }

    }


}
