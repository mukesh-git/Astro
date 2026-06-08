package com.mukeshteckwani.astro.astroapp.repository

import com.mukeshteckwani.astro.astroapp.model.TvGuideModel
import com.mukeshteckwani.astro.astroapp.utils.Commons
import com.mukeshteckwani.astro.astroapp.webhelper.AstroAPi
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvGuideRepository @Inject constructor(
    private val astroApi: AstroAPi
) {
    private var pageNumber = 1
    private var channelsIdListString: String? = null
    var channelIds: ArrayList<Int>? = null
        set(value) {
            field = value
            channelsIdListString = null
            pageNumber = 1
        }
    var sortOrder: Int = 0
    private var endTime: String? = null
    private var startTime: String? = null

    suspend fun fetchTvGuide(periodStart: String, periodEnd: String, channelIds: String): TvGuideModel? {
        return try {
            astroApi.getTvGuide(periodStart, periodEnd, channelIds)
        } catch (e: Exception) {
            null
        }
    }

    val channelIdsString: String
        get() {
            if (channelsIdListString == null) {
                val sb = StringBuilder()
                channelIds?.let { ids ->
                    for (i in ids.indices) {
                        sb.append(ids[i].toString())
                        if (i < ids.size - 1) {
                            sb.append(",")
                        }
                    }
                }
                channelsIdListString = sb.toString()
            }
            return channelsIdListString ?: ""
        }

    val currentStartTime: String
        get() {
            return if (pageNumber == 1) {
                startTime = Commons.getCurrentTime()
                startTime!!
            } else {
                startTime = Commons.addSecsToTime(
                    1, endTime, Commons.YYYY_MM_DD_HH_MM_SS_FORMAT, Commons.YYYY_MM_DD_HH_MM_SS_FORMAT
                )
                startTime!!
            }
        }

    val currentEndTime: String
        get() {
            return if (pageNumber == 1) {
                endTime = Commons.addMinsToCurrentDate(Commons.DEFAULT_TIME_INTERVAL_IN_MINS)
                endTime!!
            } else {
                endTime = Commons.addSecsToTime(
                    Commons.DEFAULT_TIME_INTERVAL_IN_MINS * 60,
                    startTime,
                    Commons.YYYY_MM_DD_HH_MM_SS_FORMAT,
                    Commons.YYYY_MM_DD_HH_MM_SS_FORMAT
                )
                endTime!!
            }
        }

    fun incrementCurrentPage() {
        pageNumber++
    }
}
