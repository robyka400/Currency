package com.nagyrobi.core.injection

import dagger.Component

/**
 * Component of the whole core exposing only the classes that should be used by other modules.
 */
@Component(modules = [RepositoryModule::class])
interface ApiComponent {
}