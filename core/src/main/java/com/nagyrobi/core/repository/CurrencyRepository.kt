package com.nagyrobi.core.repository

import com.nagyrobi.core.model.CurrencyDTO
import com.nagyrobi.core.model.CurrencyType
import com.nagyrobi.core.model.Resource
import io.reactivex.Flowable
import io.reactivex.Single

interface CurrencyRepository {

    val stream: Flowable<Resource<CurrencyDTO>>

    fun getCurrency(currencyType: CurrencyType): Single<Resource<CurrencyDTO>>

    fun fetchCurrency(currencyType: CurrencyType): Single<Resource<CurrencyDTO>>
}