package com.mukeshteckwani.astro.astroapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Commons {
    const val YYYY_MM_DD_HH_MM_SS_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val DEFAULT_TIME_INTERVAL_IN_MINS = 30

    fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_FORMAT, Locale.US)
        val date = Date()
        return dateFormat.format(date)
    }

    fun addMinsToCurrentDate(min: Int): String {
        val dateFormat = SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_FORMAT, Locale.US)
        val date = Date()
        val newDate = date.time + min * 60 * 1000L
        return dateFormat.format(newDate)
    }

    fun addSecsToTime(sec: Int, time: String?, inputFormat: String, outputFormat: String): String? {
        val format1 = SimpleDateFormat(inputFormat, Locale.US)
        val format2 = SimpleDateFormat(outputFormat, Locale.US)
        return try {
            val date = format1.parse(time)
            val newDate = date!!.time + sec * 1000L
            format2.format(newDate)
        } catch (e: Exception) {
            null
        }
    }
}
