package com.mukeshteckwani.astro.astroapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ChannelsListModel(
    @SerializedName("responseMessage")
    @Expose
    val responseMessage: String? = null,
    @SerializedName("responseCode")
    @Expose
    val responseCode: String? = null,
    @SerializedName("channels")
    @Expose
    val channels: List<Channel>? = null
) {
    data class Channel(
        @SerializedName("channelId")
        @Expose
        val channelId: Int? = null,
        @SerializedName("channelTitle")
        @Expose
        val channelTitle: String? = null,
        @SerializedName("channelStbNumber")
        @Expose
        val channelStbNumber: Int? = null,
        var isChecked: Boolean = false
    ) {
        // Default constructor required for Firebase DataSnapshot.getValue(Channel.class)
        constructor() : this(null, null, null, false)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Channel) return false
            return channelId == other.channelId
        }

        override fun hashCode(): Int {
            return channelId ?: 0
        }
    }
}
