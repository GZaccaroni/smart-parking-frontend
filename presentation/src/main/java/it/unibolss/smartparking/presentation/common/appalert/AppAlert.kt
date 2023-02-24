package it.unibolss.smartparking.presentation.common.appalert

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.presentation.common.error.message
import org.jetbrains.annotations.TestOnly
import java.util.UUID

internal sealed class AppAlert(
    val id: String = UUID.randomUUID().toString(),
    val type: AppAlertType
) {
    @get:Composable
    abstract val message: String

    override fun equals(other: Any?): Boolean =
        (other as? Error)?.id == id

    override fun hashCode(): Int = id.hashCode()

    class Error(
        @get:TestOnly
        val error: AppError,
        type: AppAlertType = AppAlertType.SNACKBAR
    ) : AppAlert(type = type) {

        override val message: String
            @Composable
            get() = error.message
    }
    class Text(
        @get:TestOnly
        @StringRes
        val text: Int,
        type: AppAlertType = AppAlertType.TOAST
    ) : AppAlert(type = type) {

        override val message: String
            @Composable
            get() = stringResource(text)
    }
}
