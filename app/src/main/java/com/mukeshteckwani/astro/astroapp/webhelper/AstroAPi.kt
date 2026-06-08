package com.mukeshteckwani.astro.astroapp.webhelper

import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel
import com.mukeshteckwani.astro.astroapp.model.TvGuideModel
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface AstroAPi {

    @GET
    suspend fun getChannelsList(
        @Url url: String,
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("sortOrder") sortOrder: Int? = null
    ): ChannelsListModel

    @GET("ams/v3/getEvents/")
    suspend fun getTvGuide(
        @Query("periodStart") periodStart: String,
        @Query("periodEnd") periodEnd: String,
        @Query("channelId") channelIds: String
    ): TvGuideModel
}
