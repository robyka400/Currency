package com.nagyrobi.currency.injection

import com.nagyrobi.currency.CurrencyApplication
import dagger.android.HasActivityInjector

/**
 * Besides defining the needed injectors for dagger, it also defines the actions that can be taken with the DI framework such as initializing.
 */
interface Injection : HasActivityInjector {

    /**
     * Initializes the DI framework. It has to be called in order to inject or inject into any other class.
     *
     * It injects the fields into the given [currencyApplication] class as well.
     */
    fun initComponent(currencyApplication: CurrencyApplication)

}