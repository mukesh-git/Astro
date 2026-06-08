package com.mukeshteckwani.astro.astroapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TvGuideModel(
    @SerializedName("responseCode")
    @Expose
    val responseCode: String? = null,
    @SerializedName("responseMessage")
    @Expose
    val responseMessage: String? = null,
    @SerializedName("getevent")
    @Expose
    val getevent: List<Getevent>? = null
) {
    data class VernacularDatum(
        @SerializedName("vernacularLanguage")
        @Expose
        val vernacularLanguage: String? = null,
        @SerializedName("vernacularProgrammeTitle")
        @Expose
        val vernacularProgrammeTitle: String? = null,
        @SerializedName("vernacularShortSynopsis")
        @Expose
        val vernacularShortSynopsis: String? = null,
        @SerializedName("vernacularLongSynopsis")
        @Expose
        val vernacularLongSynopsis: String? = null,
        @SerializedName("actors")
        @Expose
        val actors: String? = null,
        @SerializedName("directors")
        @Expose
        val directors: String? = null,
        @SerializedName("producers")
        @Expose
        val producers: String? = null
    )

    data class Getevent(
        @SerializedName("eventID")
        @Expose
        val eventID: String? = null,
        @SerializedName("channelId")
        @Expose
        val channelId: Int? = null,
        @SerializedName("channelStbNumber")
        @Expose
        val channelStbNumber: String? = null,
        @SerializedName("channelHD")
        @Expose
        val channelHD: String? = null,
        @SerializedName("channelTitle")
        @Expose
        val channelTitle: String? = null,
        @SerializedName("epgEventImage")
        @Expose
        val epgEventImage: Any? = null,
        @SerializedName("certification")
        @Expose
        val certification: String? = null,
        @SerializedName("displayDateTimeUtc")
        @Expose
        val displayDateTimeUtc: String? = null,
        @SerializedName("displayDateTime")
        @Expose
        val displayDateTime: String? = null,
        @SerializedName("displayDuration")
        @Expose
        val displayDuration: String? = null,
        @SerializedName("siTrafficKey")
        @Expose
        val siTrafficKey: String? = null,
        @SerializedName("programmeTitle")
        @Expose
        val programmeTitle: String? = null,
        @SerializedName("programmeId")
        @Expose
        val programmeId: String? = null,
        @SerializedName("episodeId")
        @Expose
        val episodeId: String? = null,
        @SerializedName("shortSynopsis")
        @Expose
        val shortSynopsis: String? = null,
        @SerializedName("longSynopsis")
        @Expose
        val longSynopsis: Any? = null,
        @SerializedName("actors")
        @Expose
        val actors: String? = null,
        @SerializedName("directors")
        @Expose
        val directors: String? = null,
        @SerializedName("producers")
        @Expose
        val producers: String? = null,
        @SerializedName("genre")
        @Expose
        val genre: String? = null,
        @SerializedName("subGenre")
        @Expose
        val subGenre: String? = null,
        @SerializedName("live")
        @Expose
        val live: Boolean? = null,
        @SerializedName("premier")
        @Expose
        val premier: Boolean? = null,
        @SerializedName("ottBlackout")
        @Expose
        val ottBlackout: Boolean? = null,
        @SerializedName("highlight")
        @Expose
        val highlight: Any? = null,
        @SerializedName("contentId")
        @Expose
        val contentId: Any? = null,
        @SerializedName("groupKey")
        @Expose
        val groupKey: Int? = null,
        @SerializedName("vernacularData")
        @Expose
        val vernacularData: List<VernacularDatum>? = null
    )
}
