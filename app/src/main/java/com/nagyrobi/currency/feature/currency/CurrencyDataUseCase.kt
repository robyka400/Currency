package com.nagyrobi.currency.feature.currency

import com.nagyrobi.core.model.CurrencyDTO
import com.nagyrobi.core.model.Resource
import com.nagyrobi.core.repository.CurrencyRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrencyDataUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {

    // todo Solve issue with flowables
    fun getStream(): Flowable<Resource<CurrencyDTO>> = currencyRepository.stream
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun startRefresh(currencyCode: String): Flowable<Resource<CurrencyDTO>> =
        currencyRepository.getCurrency(currencyCode).toFlowable()
            .concatWith(
                Flowable.interval(REFRESH_INTERVAL, TimeUnit.MILLISECONDS).startWith(1)
                    .switchMap {
                        currencyRepository.fetchCurrency(currencyCode).toFlowable()
                    }
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    companion object {
        const val REFRESH_INTERVAL = 1000L
    }
}