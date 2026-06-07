package com.mukeshteckwani.astro.astroapp.utils

import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel
import com.mukeshteckwani.astro.astroapp.model.TvGuideModel

fun sortChannels(
    channels: List<ChannelsListModel.Channel>,
    sortOrder: Int
): List<ChannelsListModel.Channel> {
    if (channels.isEmpty() || sortOrder == 0) return channels
    val sorted = channels.toMutableList()
    when (sortOrder) {
        Constants.SORT_NAME_ASC ->
            sorted.sortBy { it.channelTitle.orEmpty() }
        Constants.SORT_NAME_DESC ->
            sorted.sortByDescending { it.channelTitle.orEmpty() }
        Constants.SORT_ID_ASC ->
            sorted.sortBy { it.channelId ?: 0 }
        Constants.SORT_ID_DESC ->
            sorted.sortByDescending { it.channelId ?: 0 }
    }
    return sorted
}

fun sortTvEvents(
    events: List<TvGuideModel.Getevent>,
    sortOrder: Int
): List<TvGuideModel.Getevent> {
    if (events.isEmpty() || sortOrder == 0) return events
    val sorted = events.toMutableList()
    when (sortOrder) {
        Constants.SORT_NAME_ASC ->
            sorted.sortBy { it.channelTitle.orEmpty() }
        Constants.SORT_NAME_DESC ->
            sorted.sortByDescending { it.channelTitle.orEmpty() }
        Constants.SORT_ID_ASC ->
            sorted.sortBy { it.channelId ?: 0 }
        Constants.SORT_ID_DESC ->
            sorted.sortByDescending { it.channelId ?: 0 }
    }
    return sorted
}

fun sortOrderLabel(sortOrder: Int, forTvGuide: Boolean = false): String {
    val prefix = if (forTvGuide) "Sort: Channel " else "Sort: "
    return when (sortOrder) {
        Constants.SORT_NAME_ASC -> "${prefix}Name Ascending"
        Constants.SORT_NAME_DESC -> "${prefix}Name Descending"
        Constants.SORT_ID_ASC -> "${prefix}No. Ascending"
        Constants.SORT_ID_DESC -> "${prefix}No. Descending"
        else -> "Sort Order"
    }
}
