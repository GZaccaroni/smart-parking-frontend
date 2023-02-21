package it.unibolss.smartparking.presentation.common.snackbar

import androidx.compose.runtime.Composable
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.presentation.common.error.message
import java.util.UUID

sealed interface SnackbarContent {
    val id: String

    @get:Composable
    val message: String

    class Error(private val error: AppError) : SnackbarContent {
        override val id: String
            get() = UUID.randomUUID().toString()

        override val message: String
            @Composable
            get() = error.message

        override fun equals(other: Any?): Boolean =
            if (other is Error) (other.id == id) else false
    }
}
