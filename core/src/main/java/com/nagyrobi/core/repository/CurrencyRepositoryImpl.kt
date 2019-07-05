package com.nagyrobi.core.repository

import com.nagyrobi.core.model.CurrencyDTO
import com.nagyrobi.core.model.CurrencyType
import com.nagyrobi.core.model.Resource
import com.nagyrobi.core.networking.CurrencyService
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.processors.BehaviorProcessor
import javax.inject.Singleton

@Singleton
internal class CurrencyRepositoryImpl(
    private val currencyMemorySource: CurrencyMemorySource,
    private val currencyService: CurrencyService
) : CurrencyRepository {


    /**
     * We are using a behavior processor, since that way new subscribers get the latest value
     * (Note that in this application doesn't have any impact, since we only have one subscriber)
     * Shadowing the type enforces the streams immutability (just this class can emit through it)
     */
    private val _stream = BehaviorProcessor.create<Resource<CurrencyDTO>>()

    override val stream: Flowable<Resource<CurrencyDTO>>
        get() = _stream

    /**
     * If there is no cache fetch the currency
     *
     */
    override fun getCurrency(currencyType: CurrencyType): Single<Resource<CurrencyDTO>> =
        currencyMemorySource.getCurrency(currencyType)
            .map { Resource.Success(it) as Resource<CurrencyDTO> }
            .onErrorResumeNext(fetchCurrency(currencyType))

    /**
     * If the request is successful we cache the new currency
     */
    override fun fetchCurrency(currencyType: CurrencyType): Single<Resource<CurrencyDTO>> =
        currencyService.getCurrency(currencyType)
            .map { Resource.Success(it) as Resource<CurrencyDTO> }
            .onErrorResumeNext { Single.just(Resource.Error(it)) }

}