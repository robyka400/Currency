package com.nagyrobi.currency.feature.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nagyrobi.core.model.Resource
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CurrencyViewModel(getCurrencyStreamUseCase: GetCurrencyStreamUseCase) : ViewModel() {

    private val _currencies = MutableLiveData<List<CurrencyItem>>()
    val currencies: LiveData<List<CurrencyItem>>
        get() = _currencies


    private var disposable: Disposable = getCurrencyStreamUseCase("AUD").subscribe {
        when (it) {
            is Resource.Success -> {
                // todo handle success
                val currencies = mutableListOf(CurrencyItem(it.data.base, 1.0))
                currencies.addAll(it.data.rates.map { (type, value) -> CurrencyItem(type, value) })
                _currencies.value = currencies
            }
            is Resource.Error -> {
                // todo handle error
                println("Errooor -> ${it.error}")
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