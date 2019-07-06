package com.nagyrobi.currency

import android.app.Application
import com.nagyrobi.currency.injection.DaggerInjection
import com.nagyrobi.currency.injection.Injection

class CurrencyApplication : Application(), Injection by DaggerInjection() {

    override fun onCreate() {
        super.onCreate()
        initComponent(this)
    }
}