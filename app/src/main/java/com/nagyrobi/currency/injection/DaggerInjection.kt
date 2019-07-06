package com.nagyrobi.currency.injection

import android.app.Activity
import com.nagyrobi.currency.CurrencyApplication
import com.nagyrobi.core.injection.DaggerApiComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

class DaggerInjection : Injection {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun initComponent(currencyApplication: CurrencyApplication) {
        val apiComponent = DaggerApiComponent.factory().create()

        DaggerAppComponent.factory().create(
            currencyApplication,
            apiComponent
        ).also { it.inject(this) }
            .inject(currencyApplication)
    }
}