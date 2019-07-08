package com.nagyrobi.currency.feature.currency

import java.util.*

data class CurrencyItem(
    val symbol: String,
    var rate: Double,
    val country: String?
) {
    private val currency = Currency.getInstance(symbol)
    val name: String
        get() = currency.displayName

}