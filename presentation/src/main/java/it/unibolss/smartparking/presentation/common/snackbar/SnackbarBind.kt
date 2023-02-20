package it.unibolss.smartparking.presentation.common.snackbar

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun SharedFlow<SnackbarContent>.bind(snackbarHostState: SnackbarHostState) {
    val value = this.collectAsState(null).value ?: return

    LaunchedEffect(Unit) {
        snackbarHostState.showSnackbar(
            message = value.message
        )
    }
}
