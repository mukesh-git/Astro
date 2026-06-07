package com.mukeshteckwani.astro.astroapp.navigation

import kotlinx.serialization.Serializable

@Serializable
data object ChannelsKey

@Serializable
data class TvGuideKey(val channelIds: List<Int>)
