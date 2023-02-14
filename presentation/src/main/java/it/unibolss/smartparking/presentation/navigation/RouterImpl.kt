package it.unibolss.smartparking.presentation.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.MutableStateFlow

class RouterImpl : Router {
    override val commands = MutableStateFlow<RouterCommand?>(null)

    override fun navigateTo(route: Route, navOptions: NavOptions?) {
        commands.value = RouterCommand.NavigateTo(route, navOptions)
    }
    override fun popBackStack() {
        commands.value = RouterCommand.PopBackStack
    }
}
