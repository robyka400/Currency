package com.nagyrobi.core.repository

import com.nagyrobi.core.model.CurrencyDTO
import com.nagyrobi.core.model.CurrencyType
import io.reactivex.Flowable
import io.reactivex.Single

interface CurrencyRepository {

    val stream: Flowable<CurrencyDTO>

    fun getCurrency(currencyType: CurrencyType): Single<CurrencyDTO>

    fun fetchCurrency(currencyType: CurrencyType): Single<CurrencyDTO>
}