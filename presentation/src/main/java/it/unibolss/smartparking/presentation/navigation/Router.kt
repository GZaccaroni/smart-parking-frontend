package it.unibolss.smartparking.presentation.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.StateFlow

interface Router {
    val commands: StateFlow<RouterCommand?>
    fun navigateTo(route: Route, navOptions: NavOptions? = null)
    fun popBackStack()
}
