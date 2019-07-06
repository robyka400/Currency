package com.nagyrobi.currency.injection

import com.nagyrobi.currency.CurrencyApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    @App
    fun provideInjection(currencyApplication: CurrencyApplication) = currencyApplication
}