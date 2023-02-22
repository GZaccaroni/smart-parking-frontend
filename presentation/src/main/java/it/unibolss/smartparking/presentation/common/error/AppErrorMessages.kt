package it.unibolss.smartparking.presentation.common.error

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.presentation.R

internal val AppError.message: String
    @Composable
    get() = stringResource(id = R.string.app_name)
