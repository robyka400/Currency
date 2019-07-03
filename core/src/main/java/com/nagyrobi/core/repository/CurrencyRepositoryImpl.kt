package com.nagyrobi.core.repository

import com.nagyrobi.core.model.CurrencyDTO
import com.nagyrobi.core.model.CurrencyType
import com.nagyrobi.core.networking.CurrencyService
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
internal class CurrencyRepositoryImpl(
    private val currencyMemorySource: CurrencyMemorySource,
    private val currencyService: CurrencyService
) : CurrencyRepository {

    override val stream: Flowable<CurrencyDTO>
        get() = currencyMemorySource.stream

    /**
     * If there is no cache fetch the currency
     * TODO update this with local source
     */
    override

    fun getCurrency(currencyType: CurrencyType): Single<CurrencyDTO> = currencyMemorySource.getCurrency(currencyType)
        .onErrorResumeNext(fetchCurrency(currencyType))

    /**
     * If the request is successful we cache the new currency
     */
    override fun fetchCurrency(currencyType: CurrencyType): Single<CurrencyDTO> =
        currencyService.getCurrency(currencyType)
            .flatMap { currencyMemorySource.cache(it) }

}