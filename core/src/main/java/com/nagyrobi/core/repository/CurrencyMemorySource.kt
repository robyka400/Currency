package com.nagyrobi.core.repository

import com.nagyrobi.core.model.CurrencyDTO
import com.nagyrobi.core.model.CurrencyType
import com.nagyrobi.core.util.convertToNewBase
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.processors.BehaviorProcessor
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The in-memory cache of the currency
 * I've decided to have a separate class for this, since getting a value from cache has additional logic
 * (If we request a get with a different [com.nagyrobi.core.model.CurrencyType], we should be able to calculate
 * the values from the current cache)
 */
@Singleton
internal class CurrencyMemorySource @Inject constructor() {

    private var currency: CurrencyDTO? = null

    fun getCurrency(currencyType: CurrencyType): Single<CurrencyDTO> = Single.fromCallable {
        if (currency!!.base == currencyType) {
            currency
        } else {
            currency!!.convertToNewBase(currencyType)
        }
    }

    internal fun cache(currencyDTO: CurrencyDTO) {
        currency = currencyDTO
    }
}