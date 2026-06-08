package com.mukeshteckwani.astro.astroapp.repository;

import com.mukeshteckwani.astro.astroapp.model.TvGuideModel;
import com.mukeshteckwani.astro.astroapp.utils.Commons;
import com.mukeshteckwani.astro.astroapp.webhelper.AstroAPi;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;

@Singleton
public class TvGuideRepository {

    private final AstroAPi astroApi;
    private int pageNumber = 1;
    private StringBuilder channelsIdListString;
    private ArrayList<Integer> channelIds;
    private int sortOrder;
    private String endTime;
    private String startTime;

    @Inject
    public TvGuideRepository(AstroAPi astroApi) {
        this.astroApi = astroApi;
    }

    public TvGuideModel fetchTvGuide(String periodStart, String periodEnd, String channelIds)
            throws IOException {
        Response<TvGuideModel> response = astroApi
                .getTvGuide(periodStart, periodEnd, channelIds)
                .execute();
        return response.isSuccessful() ? response.body() : null;
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
        if (pageNumber == 1) {
            return startTime = Commons.getCurrentTime();
        }
        return startTime = Commons.addSecsToTime(
                1, endTime, Commons.YYYY_MM_DD_HH_MM_SS_FORMAT, Commons.YYYY_MM_DD_HH_MM_SS_FORMAT);
    }

    public String getEndTime() {
        if (pageNumber == 1) {
            return endTime = Commons.addMinsToCurrentDate(Commons.DEFAULT_TIME_INTERVAL_IN_MINS);
        }
        return endTime = Commons.addSecsToTime(
                Commons.DEFAULT_TIME_INTERVAL_IN_MINS * 60,
                startTime,
                Commons.YYYY_MM_DD_HH_MM_SS_FORMAT,
                Commons.YYYY_MM_DD_HH_MM_SS_FORMAT);
    }

    public void incrementCurrentPage() {
        pageNumber++;
    }

    public void setChannelIds(ArrayList<Integer> channelIds) {
        this.channelIds = channelIds;
        this.channelsIdListString = null;
        this.pageNumber = 1;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
