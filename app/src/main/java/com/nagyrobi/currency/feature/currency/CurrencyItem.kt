package com.nagyrobi.currency.feature.currency

import com.nagyrobi.currency.util.getCountryCode
import java.util.*

data class CurrencyItem(
    val symbol: String,
    val rate: Double
) {
    private val currency = Currency.getInstance(symbol)
    val name: String
        get() = currency.displayName
    val country: String?
        get() = currency.getCountryCode()

    val id: Long
        get() = currency.numericCode.toLong()
}