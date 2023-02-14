package it.unibolss.smartparking.presentation.screens.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import it.unibolss.smartparking.presentation.navigation.Route

object SignUpRoute : Route {
    override val path = "sign-up"
}

fun NavGraphBuilder.signUpScreen() {
    composable("sign-up") {
        Box(
            modifier = Modifier
        )
    }
}

