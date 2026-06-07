package com.mukeshteckwani.astro.astroapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mukeshteckwani.astro.astroapp.model.TvGuideModel;
import com.mukeshteckwani.astro.astroapp.utils.Commons;
import com.mukeshteckwani.astro.astroapp.webhelper.AstroAPi;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class TvGuideRepository {

    private final AstroAPi astroApi;
    private String lastFetchedTime;
    private int pageNumber = 1;
    private StringBuilder channelsIdListString;
    private ArrayList<Integer> channelIds;
    private int itemsCount;
    private String endTime;
    private String startTime;
    private int sortOrder;

    @Inject
    public TvGuideRepository(AstroAPi astroApi) {
        this.astroApi = astroApi;
    }

    public LiveData<TvGuideModel> getTvGuide(String periodStart, String periodEnd, String channelIds) {
        MutableLiveData<TvGuideModel> livedata = new MutableLiveData<>();
        
        astroApi.getTvGuide(periodStart, periodEnd, channelIds).enqueue(new Callback<TvGuideModel>() {
            @Override
            public void onResponse(Call<TvGuideModel> call, Response<TvGuideModel> response) {
                if (response.isSuccessful()) {
                    livedata.setValue(response.body());
                } else {
                    livedata.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<TvGuideModel> call, Throwable t) {
                livedata.setValue(null);
            }
        });
        
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
            return startTime = Commons.addSecsToTime(1, endTime, Commons.YYYY_MM_DD_HH_MM_SS_FORMAT, Commons.YYYY_MM_DD_HH_MM_SS_FORMAT);
        }
    }

    public String getEndTime() {
        if (pageNumber == 1)
            return endTime = Commons.addMinsToCurrentDate(Commons.DEFAULT_TIME_INTERVAL_IN_MINS);
        else {
            return endTime = Commons.addSecsToTime(Commons.DEFAULT_TIME_INTERVAL_IN_MINS * 60, startTime, Commons.YYYY_MM_DD_HH_MM_SS_FORMAT, Commons.YYYY_MM_DD_HH_MM_SS_FORMAT);
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
