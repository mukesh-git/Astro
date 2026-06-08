package com.mukeshteckwani.astro.astroapp.webhelper

import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel
import com.mukeshteckwani.astro.astroapp.model.TvGuideModel
import com.mukeshteckwani.astro.astroapp.utils.Constants
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockAstroApi @Inject constructor() : AstroAPi {

    private val allChannels = (1..300).map { id ->
        ChannelsListModel.Channel(
            channelId = id,
            channelTitle = "Channel $id",
            channelStbNumber = 100 + id,
            isChecked = false
        )
    }

    override suspend fun getChannelsList(
        url: String,
        page: Int?,
        pageSize: Int?,
        sortOrder: Int?
    ): ChannelsListModel {
        delay(500) // Simulate network delay

        var sortedList = when (sortOrder) {
            Constants.SORT_NAME_ASC -> allChannels.sortedBy { it.channelTitle }
            Constants.SORT_NAME_DESC -> allChannels.sortedByDescending { it.channelTitle }
            Constants.SORT_ID_ASC -> allChannels.sortedBy { it.channelId }
            Constants.SORT_ID_DESC -> allChannels.sortedByDescending { it.channelId }
            else -> allChannels
        }

        val resultList = if (page != null && pageSize != null) {
            val fromIndex = (page - 1) * pageSize
            if (fromIndex >= sortedList.size) emptyList()
            else sortedList.subList(fromIndex, minOf(fromIndex + pageSize, sortedList.size))
        } else {
            sortedList
        }

        return ChannelsListModel(
            responseMessage = "Success",
            responseCode = "200",
            channels = resultList
        )
    }

    override suspend fun getTvGuide(
        periodStart: String,
        periodEnd: String,
        channelIds: String
    ): TvGuideModel {
        // Not mocked for now, but required by interface
        return TvGuideModel(responseCode = "200", responseMessage = "Success", getevent = emptyList())
    }
}
