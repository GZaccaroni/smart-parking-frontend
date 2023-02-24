package it.unibolss.smartparking.presentation.screens.changepassword

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import it.unibolss.smartparking.presentation.navigation.Route

internal object ChangePasswordRoute : Route {
    override val path = "change-password"
}

internal fun NavGraphBuilder.changePasswordScreen() {
    composable("change-password") {
        ChangePasswordScreen()
    }
}
