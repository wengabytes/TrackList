package com.example.tracklist.utils.ext

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun String?.formatToAmount(currency: String?,
                           decimalPlaces: Int = 2,
                           local: Locale = Locale.ENGLISH): String
{
    val decimalFormatter = getNumberFormatter(
            minimumDecimalPlace = decimalPlaces,
            maximumDecimalPlace = decimalPlaces,
            local = local
    )

    val stringBuilder = StringBuilder(currency ?: "").append(" ")

    return if (this.isNullOrEmpty()) ""
    else
    {
        try
        {
            val result = this.getPlainNumber().toBigDecimal()

            try
            {
                stringBuilder.append(decimalFormatter.format(result)).toString()
            }
            catch (e: Exception)
            {
                this
            }
        }
        catch (e: NumberFormatException)
        {
            this
        } ?: ""
    }
}

fun String?.getPlainNumber(): String = this?.replace(",", "")?.replace(" ", "") ?: ""

fun getNumberFormatter(
        minimumDecimalPlace: Int = 2,
        maximumDecimalPlace: Int = 2,
        isGrouped: Boolean = true,
        local: Locale = Locale.ENGLISH
): DecimalFormat
{
    return DecimalFormat().apply {
        decimalFormatSymbols = DecimalFormatSymbols(local).apply {
            groupingSeparator = ','
            decimalSeparator = '.'
        }
        isGroupingUsed = isGrouped
        minimumFractionDigits = minimumDecimalPlace
        maximumFractionDigits = maximumDecimalPlace
        groupingSize = 3
    }
}