package com.nagyrobi.currency.injection

import com.nagyrobi.currency.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBinderModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}