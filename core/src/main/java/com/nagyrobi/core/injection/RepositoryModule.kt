package com.nagyrobi.core.injection

import com.nagyrobi.core.repository.CurrencyRepository
import com.nagyrobi.core.repository.CurrencyRepositoryImpl
import dagger.Binds
import dagger.Module

@Module(includes = [ApiModule::class])
internal abstract class RepositoryModule{

    @Binds
    internal abstract fun bindCurrencyRepository(currencyRepositoryImpl: CurrencyRepositoryImpl): CurrencyRepository
}