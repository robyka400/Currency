package com.nagyrobi.currency.injection

import javax.inject.Scope

/**
 * Identifies a type that the injector only instantiates once in the scope of the Application.
 *
 * @see javax.inject.Scope @Scope
 */
@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class App