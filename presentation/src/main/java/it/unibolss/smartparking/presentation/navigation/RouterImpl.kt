package it.unibolss.smartparking.presentation.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

internal class RouterImpl : Router {
    private val _commands = MutableSharedFlow<RouterCommand>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val commands: SharedFlow<RouterCommand> = _commands.asSharedFlow()

    override fun navigateTo(route: Route, navOptions: NavOptions?) {
        _commands.tryEmit(RouterCommand.NavigateTo(route, navOptions))
    }
    override fun popBackStack() {
        _commands.tryEmit(RouterCommand.PopBackStack)
    }
}
