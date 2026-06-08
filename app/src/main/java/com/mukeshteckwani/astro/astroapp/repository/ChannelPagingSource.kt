package com.mukeshteckwani.astro.astroapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel
import com.mukeshteckwani.astro.astroapp.webhelper.AstroAPi

class ChannelPagingSource(
    private val astroApi: AstroAPi,
    private val sortOrder: Int
) : PagingSource<Int, ChannelsListModel.Channel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChannelsListModel.Channel> {
        val page = params.key ?: 1
        return try {
            val response = astroApi.getChannelsList(
                url = "http://ams-api.astro.com.my/ams/v3/getChannelList",
                page = page,
                pageSize = params.loadSize,
                sortOrder = sortOrder
            )
            val channels = response.channels.orEmpty()
            LoadResult.Page(
                data = channels,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (channels.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ChannelsListModel.Channel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
