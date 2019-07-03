package com.nagyrobi.core.model

import com.squareup.moshi.Json

/**
 * Represents a currency and it's exchange rates
 * (Didn't include parsing of the date, since it's not used anywhere)
 */
data class CurrencyDTO(
    @Json(name = "base") val base: CurrencyType,
    @Json(name = "rates") val rates: List<Pair<CurrencyType, Double>>
)

/**
 * The possible currencies
 * (Please note, that this is not a scalable solution, I'm using this list only in order to map a Currency to an icon)
 */
enum class CurrencyType {
    EUR,
    AUD,
    BGN,
    BRL,
    CAD,
    CHF,
    CNY,
    CZK,
    DKK,
    GBP,
    HKD,
    HRK,
    HUF,
    IDR,
    ILS,
    INR,
    ISK,
    JPY,
    KRW,
    MXN,
    MYR,
    NOK,
    NZD,
    PHP,
    PLN,
    RON,
    RUB,
    SEK,
    SGD,
    THB,
    TRY,
    USD,
    ZAR
}