package com.nagyrobi.currency.feature.currency

import com.nagyrobi.core.model.CurrencyDTO
import com.nagyrobi.core.model.CurrencyType
import com.nagyrobi.core.model.Resource
import com.nagyrobi.core.repository.CurrencyRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetCurrencyStreamUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {

    private var isRefreshing = false


    operator fun invoke(currencyType: CurrencyType): Flowable<Resource<CurrencyDTO>> = currencyRepository.stream
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe {
            if (!isRefreshing) {
                startRefresh(currencyType).subscribe()
            }
        }

    /**
     *
     * Note - Flowable should be used, but apparently doesn't have a doOnDispose callback
     */
    private fun startRefresh(currencyType: CurrencyType) = Observable.interval(REFRESH_INTERVAL, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .switchMap {
            currencyRepository.fetchCurrency(currencyType).toObservable()
        }
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { isRefreshing = true }
        .doOnDispose { isRefreshing = false }


    companion object {
        const val REFRESH_INTERVAL = 1000L
    }
}