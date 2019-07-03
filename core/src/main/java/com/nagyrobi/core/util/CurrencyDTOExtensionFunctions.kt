package com.nagyrobi.core.util

import com.nagyrobi.core.model.CurrencyDTO
import com.nagyrobi.core.model.CurrencyType

internal fun CurrencyDTO.convertToNewBase(currencyType: CurrencyType): CurrencyDTO {
    val newCurrencyRate = rates.first { (type, _) -> type == currencyType }.second
    val newRates = mutableListOf(base to (1 / newCurrencyRate))

    rates.forEach { (type, rate) ->
        newRates.add(type to rate / newCurrencyRate)
    }

    return CurrencyDTO(base = currencyType, rates = newRates)
}