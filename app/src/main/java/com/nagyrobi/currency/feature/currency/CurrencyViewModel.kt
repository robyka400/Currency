package com.nagyrobi.currency.feature.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nagyrobi.core.model.CurrencyType
import com.nagyrobi.core.model.Resource
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CurrencyViewModel(getCurrencyStreamUseCase: GetCurrencyStreamUseCase) : ViewModel() {

    private var disposable: Disposable = getCurrencyStreamUseCase(CurrencyType.AUD).subscribe {
        when (it) {
            is Resource.Success -> {
                // todo handle success
                it.data.rates.forEach { (type, rate) ->
                    println("$type - $rate")
                }
            }
            is Resource.Error -> {
                // todo handle error
                println("Errooor -> ${it.error}" )
            }
        }
    }

    override fun onCleared() {
        disposable.dispose()
    }

    class Factory @Inject constructor(private val getCurrencyStreamUseCase: GetCurrencyStreamUseCase) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CurrencyViewModel(getCurrencyStreamUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.canonicalName}")
        }

    }
}