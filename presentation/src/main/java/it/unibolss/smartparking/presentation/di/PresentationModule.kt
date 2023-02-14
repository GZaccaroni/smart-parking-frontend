package it.unibolss.smartparking.presentation.di

import it.unibolss.smartparking.presentation.navigation.Router
import it.unibolss.smartparking.presentation.navigation.RouterImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val navigationModule = module {
    singleOf<Router>(::RouterImpl)
}
internal val viewModelsModule = module {
}

val presentationModule get() = navigationModule + viewModelsModule
