package it.unibolss.smartparking.data.di

import org.koin.dsl.module

internal val dataSourceModule = module {
}

internal val repositoryModule = module {
}

val dataModule get() = dataSourceModule + repositoryModule