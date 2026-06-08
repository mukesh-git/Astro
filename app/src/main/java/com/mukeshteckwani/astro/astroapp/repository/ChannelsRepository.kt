package com.mukeshteckwani.astro.astroapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mukeshteckwani.astro.astroapp.data.PreferenceManager
import com.mukeshteckwani.astro.astroapp.db.FavouriteChannelDao
import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel
import com.mukeshteckwani.astro.astroapp.model.toChannel
import com.mukeshteckwani.astro.astroapp.model.toEntity
import com.mukeshteckwani.astro.astroapp.webhelper.AstroAPi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChannelsRepository @Inject constructor(
    private val astroApi: AstroAPi,
    private val favouriteChannelDao: FavouriteChannelDao,
    private val preferenceManager: PreferenceManager
) {
    fun getChannelList(sortOrder: Int): Flow<PagingData<ChannelsListModel.Channel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ChannelPagingSource(astroApi, sortOrder) }
        ).flow
    }

    fun getAllChannels(): Flow<List<ChannelsListModel.Channel>> = flow {
        try {
            val response = astroApi.getChannelsList(
                url = "http://ams-api.astro.com.my/ams/v3/getChannelList"
            )
            emit(response.channels.orEmpty())
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    val favouriteChannels: Flow<List<ChannelsListModel.Channel>> = favouriteChannelDao.getAllFavourites()
        .map { entities ->
            entities.map { it.toChannel() }
        }

    suspend fun writeOrRemoveChannelsData(channel: ChannelsListModel.Channel) {
        if (channel.isChecked) {
            channel.toEntity()?.let {
                favouriteChannelDao.insert(it)
            }
        } else {
            channel.channelId?.let {
                favouriteChannelDao.deleteById(it)
            }
        }
    }

    val sortOrder: Flow<Int> = preferenceManager.sortOrder

    suspend fun setSortOrder(sortOrder: Int) {
        preferenceManager.setSortOrder(sortOrder)
    }
}
