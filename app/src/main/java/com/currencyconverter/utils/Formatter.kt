package com.currencyconverter.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

val mDF = DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).also {
    it.maximumFractionDigits = 340
}

fun String.format(): String =
    if (this.isEmpty())
        "0"
    else
        mDF.format(this.toDouble())

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) {
        multiplier *= 10
    }
    return kotlin.math.round(this * multiplier) / multiplier
}