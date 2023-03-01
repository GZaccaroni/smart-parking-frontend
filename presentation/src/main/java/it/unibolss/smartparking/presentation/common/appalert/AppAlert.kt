package it.unibolss.smartparking.presentation.common.appalert

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.presentation.common.error.message
import org.jetbrains.annotations.TestOnly

sealed interface AppAlert {
    @get:Composable
    val message: String

    val type: AppAlertType

    data class Error(
        @get:TestOnly
        val error: AppError,
        override val type: AppAlertType = AppAlertType.SNACKBAR
    ) : AppAlert {

        override val message: String
            @Composable
            get() = error.message
    }
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
