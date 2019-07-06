package com.nagyrobi.currency.injection

import android.app.Application
import com.nagyrobi.core.injection.ApiComponent
import com.nagyrobi.currency.CurrencyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@App
@Component(modules = [AndroidInjectionModule::class, ActivityBinderModule::class], dependencies = [ApiComponent::class])
interface AppComponent: AndroidInjector<CurrencyApplication> {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: Application, apiComponent: ApiComponent): AppComponent
    }

    fun inject(injection: DaggerInjection)
}