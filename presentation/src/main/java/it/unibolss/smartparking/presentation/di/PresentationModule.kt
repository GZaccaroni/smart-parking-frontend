package it.unibolss.smartparking.presentation.di

import it.unibolss.smartparking.presentation.navigation.Router
import it.unibolss.smartparking.presentation.navigation.RouterImpl
import it.unibolss.smartparking.presentation.screens.user.changepassword.ChangePasswordScreenViewModel
import it.unibolss.smartparking.presentation.screens.user.login.LoginScreenViewModel
import it.unibolss.smartparking.presentation.screens.parkingslot.parkingslot.ParkingSlotScreenViewModel
import it.unibolss.smartparking.presentation.screens.parkingslot.parkingslots.ParkingSlotsScreenViewModel
import it.unibolss.smartparking.presentation.screens.user.signup.SignUpScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
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
    viewModelOf(::ChangePasswordScreenViewModel)

    // Parking Slots
    viewModel { parameters ->
        ParkingSlotScreenViewModel(parkingSlotId = parameters.get(), get(), get(), get(), get(), get())
    }
    viewModelOf(::ParkingSlotsScreenViewModel)
}

val presentationModule get() = navigationModule + viewModelsModule
