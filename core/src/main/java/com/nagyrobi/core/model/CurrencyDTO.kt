package com.nagyrobi.core.model

import com.squareup.moshi.Json

/**
 * Represents a currency and it's exchange rates
 * (Didn't include parsing of the date, since it's not used anywhere)
 */
data class CurrencyDTO(
    @Json(name = "base") val base: String,
    @Json(name = "rates") val rates: Map<String, Double>
)