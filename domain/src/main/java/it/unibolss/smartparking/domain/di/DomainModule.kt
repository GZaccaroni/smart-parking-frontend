package it.unibolss.smartparking.domain.di

import it.unibolss.smartparking.domain.usecases.parkingslot.FindParkingSlots
import it.unibolss.smartparking.domain.usecases.parkingslot.FreeParkingSlot
import it.unibolss.smartparking.domain.usecases.parkingslot.IncrementParkingSlotOccupation
import it.unibolss.smartparking.domain.usecases.parkingslot.OccupyParkingSlot
import it.unibolss.smartparking.domain.usecases.parkingslot.ViewParkingSlot
import it.unibolss.smartparking.domain.usecases.user.ChangeUserPassword
import it.unibolss.smartparking.domain.usecases.user.DeleteUser
import it.unibolss.smartparking.domain.usecases.user.GetUser
import it.unibolss.smartparking.domain.usecases.user.LoginUser
import it.unibolss.smartparking.domain.usecases.user.LogoutUser
import it.unibolss.smartparking.domain.usecases.user.GetAuthState
import it.unibolss.smartparking.domain.usecases.user.SignUpUser
import it.unibolss.smartparking.domain.usecases.user.ValidateUserEmail
import it.unibolss.smartparking.domain.usecases.user.ValidateUserName
import it.unibolss.smartparking.domain.usecases.user.ValidateUserPassword

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(::FindParkingSlots)
    singleOf(::FreeParkingSlot)
    singleOf(::IncrementParkingSlotOccupation)
    singleOf(::OccupyParkingSlot)
    singleOf(::ViewParkingSlot)
    singleOf(::ChangeUserPassword)
    singleOf(::DeleteUser)
    singleOf(::GetUser)
    singleOf(::LoginUser)
    singleOf(::LogoutUser)
    singleOf(::GetAuthState)
    singleOf(::SignUpUser)
    singleOf(::ValidateUserEmail)
    singleOf(::ValidateUserName)
    singleOf(::ValidateUserPassword)
}

val domainModule get() = useCaseModule