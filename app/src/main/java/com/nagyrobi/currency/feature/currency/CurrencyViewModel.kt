package com.nagyrobi.currency.feature.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nagyrobi.core.model.Resource
import io.reactivex.disposables.Disposable
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class CurrencyViewModel(getCurrencyStreamUseCase: GetCurrencyStreamUseCase) : ViewModel() {

    private val countryCodes = mutableMapOf(
        EURO_CURRENCY to EURO_COUNTRY,
        *(NumberFormat.getAvailableLocales().map {
            NumberFormat.getCurrencyInstance(it).currency?.currencyCode to it.country
        }.filter { (currencyCode, _) -> currencyCode != EURO_CURRENCY }.toTypedArray())
    )

    private val _currencies = MutableLiveData<List<CurrencyItem>>()
    val currencies: LiveData<List<CurrencyItem>>
        get() = _currencies


    private var disposable: Disposable = getCurrencyStreamUseCase("AUD").subscribe {
        when (it) {
            is Resource.Success -> {
                // todo handle success
                val currencies = mutableListOf(CurrencyItem(it.data.base, 1.0, countryCodes[it.data.base]))
                currencies.addAll(it.data.rates.map { (currency, value) ->
                    CurrencyItem(
                        currency,
                        value,
                        countryCodes[currency]
                    )
                })
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

    companion object {
        private const val EURO_CURRENCY = "EUR"
        private const val EURO_COUNTRY = "EU"
    }
}