package com.nagyrobi.core.injection

import com.nagyrobi.core.networking.CurrencyService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit
import retrofit2.create

/**
 * Module providing the RetrofitService implementation.
 * todo
 */
@Module(includes = [NetworkModule::class])
internal class ApiModule {

    @Provides
    @Reusable
    internal fun createCurrencyService(retrofit: Retrofit): CurrencyService = retrofit.create()
}