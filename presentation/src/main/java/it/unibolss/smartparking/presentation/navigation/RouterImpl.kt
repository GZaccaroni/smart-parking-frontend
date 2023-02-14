package it.unibolss.smartparking.presentation.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RouterImpl : Router {
    override val commands: StateFlow<RouterCommand?>
        get() = mutableCommands

    private val mutableCommands = MutableStateFlow<RouterCommand?>(null)

    override fun navigateTo(route: Route, navOptions: NavOptions?) {
        mutableCommands.value = RouterCommand.NavigateTo(route, navOptions)
    }
    override fun popBackStack() {
        mutableCommands.value = RouterCommand.PopBackStack
    }
}
