package it.unibolss.smartparking.presentation.navigation

import androidx.navigation.NavOptions

/**
 * This interface represents a command that can be given to a [Router] instance
 */
sealed interface RouterCommand {

    /**
     * This class represents the requests to a [Router] instance to navigate to a specific [route]
     * with some [navOptions]
     */
    data class NavigateTo(
        val route: Route,
        val navOptions: NavOptions? = null,
    ) : RouterCommand

    /**
     * This object represents the requests to a [Router] instance to pop the current screen
     */
    object PopBackStack : RouterCommand
}
