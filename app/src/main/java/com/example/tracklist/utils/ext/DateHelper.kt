package com.example.tracklist.utils.ext

import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT_STANDARD_NO_TIME = "MM/dd/yyyy"
const val DATE_FORMAT_STANDARD = "MM/dd/yyyy hh:mm:ss a"
const val DATE_FORMAT_STANDARD_NO_AM_PM = "MM/dd/yyyy hh:mm:ss"
const val DATE_FORMAT_DATE_WHOLE = "MMMM d, yyyy"
const val DATE_FORMAT_DATE_WHOLE_AM_PM = "MMMM d, yyyy hh:mm a"
const val DATE_FORMAT_MONTH_YEAR = "MMMM yyyy"
const val DATE_FORMAT_DATE_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss.S"
const val DATE_FORMAT_DATE_TIMEZONE_NO_S = "yyyy-MM-dd'T'HH:mm:ss"
const val DATE_FORMAT_DATE_NO_TIME = "yyyy-MM-dd"
const val DATE_YYYY_MM_DD = "yyyy-MM-dd"

fun Any?.getCurrentDate(): Date =
        Calendar.getInstance().apply { add(Calendar.DATE, 0) }.time

fun Date?.getFormattedStringDate(dateFormat: String = DATE_FORMAT_DATE_WHOLE,
                                 local: Locale = Locale.ENGLISH) = try
{
    if (this == null)
        ""
    else
        SimpleDateFormat(dateFormat, local).format(this)
}
catch (e: Exception)
{
    ""
}

fun String?.changeStringDateFormat(from: String,
                                   to: String = DATE_FORMAT_DATE_WHOLE,
                                   local: Locale = Locale.ENGLISH): String =
        if (this.isNullOrEmpty() || from.isEmpty())
            ""
        else
            try
            {
                val date = convertToDate(from, local)
                date?.let { SimpleDateFormat(to, local).format(it) }
                date?.let { SimpleDateFormat(to, local).format(it) }
            }
            catch (e: Exception)
            {
                ""
            } ?: ""

fun String?.convertToDate(from: String,
                          local: Locale = Locale.ENGLISH): Date? =
        if (this.isNullOrEmpty() || from.isEmpty())
            null
        else
            try
            {
                SimpleDateFormat(from, local).parse(this)
            }
            catch (e: Exception)
            {
                null
            }