package it.unibolss.smartparking.presentation.common.appalert

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.presentation.common.error.message
import org.jetbrains.annotations.TestOnly

/**
 * An [AppAlert] represents a message that should be displayed to the user to
 * convey errors or warnings.
*/
sealed interface AppAlert {

    /**
     * Message of the alert
     */
    @get:Composable
    val message: String

    /**
     * Type defines how the [AppAlert] should be shown
     */
    val type: AppAlertType

    /**
     * An [AppAlert.Error] represents a message that should be displayed to the user to
     * convey errors.
     */
    data class Error(
        @get:TestOnly
        val error: AppError,
        override val type: AppAlertType = AppAlertType.SNACKBAR
    ) : AppAlert {

        override val message: String
            @Composable
            get() = error.message
    }

    /**
     * An [AppAlert.Text] represents a message that should be displayed to the user to
     * convey warnings.
     */
    data class Text(
        @get:TestOnly
        @StringRes
        val text: Int,
        override val type: AppAlertType = AppAlertType.TOAST
    ) : AppAlert {

        override val message: String
            @Composable
            get() = stringResource(text)
    }
}
