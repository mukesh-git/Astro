package com.mukeshteckwani.astro.astroapp.webhelper;

import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel;
import com.mukeshteckwani.astro.astroapp.model.TvGuideModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by mukeshteckwani on 17/12/17.
 */

public interface AstroAPi {

    @GET
    Call<ChannelsListModel> getChannelsList(@Url String url);

    @GET("ams/v3/getEvents/")
    Call<TvGuideModel> getTvGuide(@Query("periodStart") String periodStart,@Query("periodEnd") String periodEnd,@Query("channelId") String channelIds);
}
