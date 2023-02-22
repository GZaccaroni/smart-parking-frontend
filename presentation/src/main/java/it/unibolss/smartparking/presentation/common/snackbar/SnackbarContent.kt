package it.unibolss.smartparking.presentation.common.snackbar

import androidx.compose.runtime.Composable
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.presentation.common.error.message
import org.jetbrains.annotations.TestOnly
import java.util.UUID

internal sealed interface SnackbarContent {
    val id: String

    @get:Composable
    val message: String

    class Error(
        @get:TestOnly
        val error: AppError
    ) : SnackbarContent {
        override val id: String
            get() = UUID.randomUUID().toString()

        override val message: String
            @Composable
            get() = error.message

        override fun equals(other: Any?): Boolean =
            if (other is Error) (other.id == id) else false

        override fun hashCode(): Int = id.hashCode()
    }
}
