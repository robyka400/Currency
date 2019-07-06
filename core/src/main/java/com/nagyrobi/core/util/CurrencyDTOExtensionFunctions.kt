package com.nagyrobi.core.util

import com.nagyrobi.core.model.CurrencyDTO

internal fun CurrencyDTO.convertToNewBase(currencyCode: String): CurrencyDTO {
    val newCurrencyRate = rates.entries.first { it.key == currencyCode }.value
    val newRates = mutableMapOf(base to 1 / newCurrencyRate)

    rates.forEach { (type, rate) ->
        newRates[type] = rate / newCurrencyRate
    }

    return CurrencyDTO(base = currencyCode, rates = newRates)
}