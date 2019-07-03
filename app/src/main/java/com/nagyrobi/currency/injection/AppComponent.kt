package com.nagyrobi.currency.injection

import com.nagyrobi.core.injection.ApiComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class], dependencies = [ApiComponent::class])
interface AppComponent {
}