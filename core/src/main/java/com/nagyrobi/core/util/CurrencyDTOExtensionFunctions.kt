package com.nagyrobi.core.util

import com.nagyrobi.core.model.CurrencyDTO
import com.nagyrobi.core.model.CurrencyType

internal fun CurrencyDTO.convertToNewBase(currencyType: CurrencyType): CurrencyDTO {
    val newCurrencyRate = rates.entries.first { it.key == currencyType }.value
    val newRates = mutableMapOf(base to 1 / newCurrencyRate)

    rates.forEach { (type, rate) ->
        newRates[type] = rate / newCurrencyRate
    }

    return CurrencyDTO(base = currencyType, rates = newRates)
}