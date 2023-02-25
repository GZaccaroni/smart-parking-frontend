package it.unibolss.smartparking.presentation.di

import it.unibolss.smartparking.presentation.navigation.Router
import it.unibolss.smartparking.presentation.navigation.RouterImpl
import it.unibolss.smartparking.presentation.screens.changepassword.ChangePasswordScreenViewModel
import it.unibolss.smartparking.presentation.screens.login.LoginScreenViewModel
import it.unibolss.smartparking.presentation.screens.signup.SignUpScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val navigationModule = module {
    singleOf<Router>(::RouterImpl)
}
internal val viewModelsModule = module {
    // User
    viewModelOf(::LoginScreenViewModel)
    viewModelOf(::SignUpScreenViewModel)

    // Parking Slots
    viewModelOf(::ChangePasswordScreenViewModel)
}

val presentationModule get() = navigationModule + viewModelsModule
