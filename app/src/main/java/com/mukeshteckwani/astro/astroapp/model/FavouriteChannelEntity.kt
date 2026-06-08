package com.mukeshteckwani.astro.astroapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_channels")
data class FavouriteChannelEntity(
    @PrimaryKey val channelId: Int,
    val channelTitle: String?,
    val channelStbNumber: Int?
)

fun ChannelsListModel.Channel.toEntity(): FavouriteChannelEntity? {
    return channelId?.let {
        FavouriteChannelEntity(
            channelId = it,
            channelTitle = channelTitle,
            channelStbNumber = channelStbNumber
        )
    }
}

fun FavouriteChannelEntity.toChannel(): ChannelsListModel.Channel {
    return ChannelsListModel.Channel(
        channelId = channelId,
        channelTitle = channelTitle,
        channelStbNumber = channelStbNumber,
        isChecked = true
    )
}
