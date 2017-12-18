package com.mukeshteckwani.astro.astroapp.webhelper;

import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by mukeshteckwani on 17/12/17.
 */

public interface AstroAPi {

    @GET
    Call<ChannelsListModel> getChannelsList(@Url String url);
}
