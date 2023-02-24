package it.unibolss.smartparking.presentation.common.appalert

import android.widget.Toast
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.SharedFlow

@Composable
internal fun SharedFlow<AppAlert>.Bind(snackbarHostState: SnackbarHostState) {
    val value = this.collectAsState(null).value ?: return
    val message = value.message
    when (value.type) {
        AppAlertType.TOAST -> {
            val context = LocalContext.current
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
        AppAlertType.SNACKBAR ->
            LaunchedEffect(value) {
                snackbarHostState.showSnackbar(
                    message = message
                )
            }
    }
}
