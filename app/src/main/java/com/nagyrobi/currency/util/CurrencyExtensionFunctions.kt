package com.nagyrobi.currency.util

import java.text.NumberFormat
import java.util.*

// TODO cache these values
fun Currency.getCountryCode(): String? =
    NumberFormat.getAvailableLocales().firstOrNull {
        NumberFormat.getCurrencyInstance(it).currency?.currencyCode == currencyCode
    }?.country
