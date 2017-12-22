package com.mukeshteckwani.astro.astroapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.mukeshteckwani.astro.astroapp.model.TvGuideModel;
import com.mukeshteckwani.astro.astroapp.utils.Commons;
import com.mukeshteckwani.astro.astroapp.webhelper.RetrofitService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by mukeshteckwani on 21/12/17.
 */

public class TvGuideViewModel extends AndroidViewModel {

    private String lastFetchedTime;
    private int pageNumber = 1;
    private StringBuilder channelsIdListString;
    private ArrayList<Integer> channelIds;
    private int itemsCount;
    private String endTime;
    private String startTime;
    private int sortOrder;

    public TvGuideViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<TvGuideModel> getTvGuide(String periodStart, String periodEnd, String channelIds) {
        MutableLiveData<TvGuideModel> livedata = new MutableLiveData<>();
        RetorfitCallback<TvGuideModel> callback = new RetorfitCallback<>(getApplication(),
                new RetorfitCallback.Listener<TvGuideModel>() {
                    @Override
                    public void onResponse(Call<TvGuideModel> call, TvGuideModel response) {
                        livedata.setValue(response);
                    }

                    @Override
                    public void onFailure(Call<TvGuideModel> call, Throwable t) {
                        livedata.setValue(null);
                    }

                    @Override
                    public void onResponseHeaders(Map<String, List<String>> headers) {

                    }
                });
        RetrofitService.getApiService().getTvGuide(periodStart, periodEnd, channelIds).enqueue(callback);
        return livedata;
    }

    public String getLastFetchedTime() {
        return lastFetchedTime;
    }

    public void setLastFetchedTime(String lastFetchedTime) {
        this.lastFetchedTime = lastFetchedTime;
    }

    public String getChannelIdsString() {
        if (channelsIdListString == null) {
            channelsIdListString = new StringBuilder();
            for (Integer channelId : channelIds) {
                channelsIdListString.append(String.valueOf(channelId));
                if (channelIds.indexOf(channelId) < channelIds.size() - 1) {
                    channelsIdListString.append(",");
                }
            }
        }
        return channelsIdListString.toString();
    }

    public String getStartTime() {
        if (pageNumber == 1)
            return startTime = Commons.getCurrentTime();
        else {
            return startTime = Commons.addSecsToTime(1,endTime,Commons.YYYY_MM_DD_HH_MM_SS_FORMAT,Commons.YYYY_MM_DD_HH_MM_SS_FORMAT);
        }
    }

    public String getEndTime() {
        if (pageNumber == 1)
            return endTime = Commons.addMinsToCurrentDate(Commons.DEFAULT_TIME_INTERVAL_IN_MINS);
        else {
            return endTime = Commons.addSecsToTime(Commons.DEFAULT_TIME_INTERVAL_IN_MINS * 60,startTime,Commons.YYYY_MM_DD_HH_MM_SS_FORMAT,Commons.YYYY_MM_DD_HH_MM_SS_FORMAT);
        }
    }

    public void incrementCurrentPage() {
        pageNumber++;
    }

    public void setChannelIds(ArrayList<Integer> channelIds) {
        this.channelIds = channelIds;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
