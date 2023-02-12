package it.unibo.smart_parking.domain.di

import org.koin.dsl.module

internal val useCaseModule = module {
}

val domainModule get() = useCaseModule