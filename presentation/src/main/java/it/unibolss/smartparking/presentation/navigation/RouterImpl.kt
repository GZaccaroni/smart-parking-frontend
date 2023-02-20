package it.unibolss.smartparking.presentation.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RouterImpl : Router {
    private val _commands = MutableStateFlow<RouterCommand?>(null)
    override val commands: StateFlow<RouterCommand?> = _commands.asStateFlow()

    override fun navigateTo(route: Route, navOptions: NavOptions?) {
        _commands.value = RouterCommand.NavigateTo(route, navOptions)
    }
    override fun popBackStack() {
        _commands.value = RouterCommand.PopBackStack
    }
}
