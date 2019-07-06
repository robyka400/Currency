package com.nagyrobi.currency.util

import java.text.NumberFormat
import java.util.*

// TODO cache these values
fun Currency.getCountryCode(): String? = if (currencyCode == EURO_CURRENCY) EURO_COUNTRY else
    NumberFormat.getAvailableLocales().firstOrNull {
        NumberFormat.getCurrencyInstance(it).currency?.currencyCode == currencyCode
    }?.country

private const val EURO_CURRENCY = "EUR"
private const val EURO_COUNTRY = "EU"
