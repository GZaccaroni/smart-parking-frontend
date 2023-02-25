package it.unibolss.smartparking.presentation.navigation

import androidx.navigation.NavOptions

sealed interface RouterCommand {
    data class NavigateTo(
        val route: Route,
        val navOptions: NavOptions? = null,
    ) : RouterCommand
    object PopBackStack : RouterCommand
}
