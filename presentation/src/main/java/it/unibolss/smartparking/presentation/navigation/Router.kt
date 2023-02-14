package it.unibolss.smartparking.presentation.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.MutableStateFlow

class Router {
    val commands = MutableStateFlow<RouterCommand?>(null)

    fun navigateTo(route: Route, navOptions: NavOptions? = null) {
        commands.value = RouterCommand.NavigateTo(route, navOptions)
    }
    fun popBackStack() {
        commands.value = RouterCommand.PopBackStack
    }
}
