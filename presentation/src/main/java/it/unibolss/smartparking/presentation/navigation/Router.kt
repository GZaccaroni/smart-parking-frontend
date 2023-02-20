package it.unibolss.smartparking.presentation.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.SharedFlow

interface Router {
    val commands: SharedFlow<RouterCommand>
    fun navigateTo(route: Route, navOptions: NavOptions? = null)
    fun popBackStack()
}
