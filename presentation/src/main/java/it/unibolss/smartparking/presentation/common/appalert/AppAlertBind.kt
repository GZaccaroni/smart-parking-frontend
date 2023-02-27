package it.unibolss.smartparking.presentation.common.appalert

import android.widget.Toast
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
internal fun AppAlertState.Bind(snackbarHostState: SnackbarHostState) {
    if (this !is AppAlertState.Some) return
    val message = this.alert.message
    when (this.alert.type) {
        AppAlertType.TOAST -> {
            val context = LocalContext.current
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
        AppAlertType.SNACKBAR ->
            LaunchedEffect(this.alert) {
                snackbarHostState.showSnackbar(
                    message = message
                )
            }
    }
}
