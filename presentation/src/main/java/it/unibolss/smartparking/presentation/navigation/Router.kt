package it.unibolss.smartparking.presentation.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.SharedFlow

/**
 * An interface that defines the contract for a router, in particular it exposes the [commands]
 * received from the methods it exposes ([navigateTo] and [popBackStack])
 */
interface Router {
    val commands: SharedFlow<RouterCommand>

    /**
     * Navigates to a specific [route] respecting [navOptions]
     */
    fun navigateTo(route: Route, navOptions: NavOptions? = null)

    /**
     * Pops the current screen
     */
    fun popBackStack()
}
