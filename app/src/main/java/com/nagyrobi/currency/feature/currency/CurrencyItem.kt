package com.nagyrobi.currency.feature.currency

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nagyrobi.core.model.CurrencyType
import com.nagyrobi.core.model.CurrencyType.*
import com.nagyrobi.currency.R

data class CurrencyItem(
    val symbol: CurrencyType,
    val rate: Double
) {
    val logoId: Int
        get() = R.mipmap.ic_launcher

    val name: Int
        get() = when (symbol) {
            EUR -> R.string.EUR
            AUD -> R.string.AUD
            BGN -> R.string.BGN
            BRL -> R.string.BRL
            CAD -> R.string.CAD
            CHF -> R.string.CHF
            CNY -> R.string.CNY
            CZK -> R.string.CZK
            DKK -> R.string.DKK
            GBP -> R.string.GBP
            HKD -> R.string.HKD
            HRK -> R.string.HRK
            HUF -> R.string.HUF
            IDR -> R.string.IDR
            ILS -> R.string.ILS
            INR -> R.string.INR
            ISK -> R.string.ISK
            JPY -> R.string.JPY
            KRW -> R.string.KRW
            MXN -> R.string.MXN
            MYR -> R.string.MYR
            NOK -> R.string.NOK
            NZD -> R.string.NZD
            PHP -> R.string.PHP
            PLN -> R.string.PLN
            RON -> R.string.RON
            RUB -> R.string.RUB
            SEK -> R.string.SEK
            SGD -> R.string.SGD
            THB -> R.string.THB
            TRY -> R.string.TRY
            USD -> R.string.USD
            ZAR -> R.string.ZAR
        }
}