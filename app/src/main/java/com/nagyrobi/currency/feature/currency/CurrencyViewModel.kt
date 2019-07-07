package com.nagyrobi.currency.feature.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nagyrobi.core.model.CurrencyDTO
import com.nagyrobi.core.model.Resource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class CurrencyViewModel(currencyDataUseCase: CurrencyDataUseCase) : ViewModel() {

    private val countryCodes = mutableMapOf(
        EURO_CURRENCY to EURO_COUNTRY,
        *(NumberFormat.getAvailableLocales().map {
            NumberFormat.getCurrencyInstance(it).currency?.currencyCode to it.country
        }.filter { (currencyCode, _) -> currencyCode != EURO_CURRENCY }.toTypedArray())
    )

    private val _currencies = MutableLiveData<List<CurrencyItem>>()
    val currencies: LiveData<List<CurrencyItem>>
        get() = _currencies

    val selectedCurrency = MutableLiveData<String>()

    private val streamDisposable = currencyDataUseCase.getStream().subscribe {
        when (it) {
            is Resource.Success -> {
                _currencies.value = if (_currencies.value == null) {
                    mapCurrencies(it.data)
                } else {
                    updateCurrencies(it.data)
                }
            }
            is Resource.Error -> {
                // todo handle error
                println("Errooor -> ${it.error}")
            }
        }
    }
    private var refreshDisposable: Disposable? = null

    private val observer = Observer<String> { currencyCode ->
        refreshDisposable?.dispose()
        refreshDisposable = currencyDataUseCase.startRefresh(currencyCode).subscribe()
    }

    init {
        selectedCurrency.observeForever(observer)
    }

    override fun onCleared() {
        streamDisposable.dispose()
        refreshDisposable?.dispose()
        selectedCurrency.removeObserver(observer)
    }

    class Factory @Inject constructor(private val currencyDataUseCase: CurrencyDataUseCase) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CurrencyViewModel(currencyDataUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.canonicalName}")
        }

    }

    fun setCurrentLocale(locale: Locale) {
        selectedCurrency.value = NumberFormat.getCurrencyInstance(locale).currency?.currencyCode
    }

    private fun mapCurrencies(newCurrencies: CurrencyDTO): List<CurrencyItem> {
        val currencies = mutableListOf(CurrencyItem(newCurrencies.base, 1.0, countryCodes[newCurrencies.base]))
        currencies.addAll(newCurrencies.rates.map { (currency, value) ->
            CurrencyItem(
                currency,
                value,
                countryCodes[currency]
            )
        })

        return currencies
    }

    private fun updateCurrencies(newCurrencies: CurrencyDTO): List<CurrencyItem> {
        val currentCurrencies = _currencies.value ?: return emptyList()
        val currencies = mutableListOf(currentCurrencies.first { it.symbol == newCurrencies.base }.copy(rate = 1.0))
        currencies.addAll(currentCurrencies.filterNot { it.symbol == newCurrencies.base }.map {
            it.copy(rate = newCurrencies.rates[it.symbol] ?: 0.0)
        })

        return currencies
    }

    companion object {
        private const val EURO_CURRENCY = "EUR"
        private const val EURO_COUNTRY = "EU"
    }
}