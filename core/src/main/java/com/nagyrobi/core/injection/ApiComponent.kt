package com.nagyrobi.core.injection

import com.nagyrobi.core.repository.CurrencyRepository
import dagger.Component
import javax.inject.Singleton

/**
 * Component of the whole core exposing only the classes that should be used by other modules.
 */

@Singleton
@Component(modules = [RepositoryModule::class])
interface ApiComponent {

    @Component.Factory
    interface Factory {
        fun create(): ApiComponent
    }

    fun currencyRepository(): CurrencyRepository
}