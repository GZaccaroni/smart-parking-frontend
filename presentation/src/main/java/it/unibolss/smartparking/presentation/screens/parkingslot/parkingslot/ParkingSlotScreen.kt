package it.unibolss.smartparking.presentation.screens.parkingslot.parkingslot

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.location.Location
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.unibolss.smartparking.domain.entities.geo.GeoPosition
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlotState
import it.unibolss.smartparking.presentation.R
import it.unibolss.smartparking.presentation.common.appalert.AppAlertState
import it.unibolss.smartparking.presentation.common.appalert.Bind
import it.unibolss.smartparking.presentation.common.date.formatted
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.annotations.TestOnly
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.time.format.FormatStyle
import kotlin.time.Duration.Companion.hours

/**
 * UI of the parking slot screen.
 * @param vm view model of the parking slot screen.
 */
@Composable
fun ParkingSlotScreen(
    parkingSlotId: String,
    vm: ParkingSlotScreenViewModel = koinViewModel { parametersOf(parkingSlotId) }
) {
    val uiState by vm.uiState.collectAsState()
    val alertState by vm.alertState.collectAsState()

    ParkingSlotLayout(
        uiState,
        alertState = alertState,
        onOccupyClicked = {
            vm.occupy(it)
        },
        onIncrementOccupationClicked = {
            vm.incrementOccupation(it)
        },
        onFreeClicked = {
            vm.free()
        },
        onRefreshClicked = {
            vm.refresh()
        },
        onBackClicked = {
            vm.goBack()
        },
    )
}

@Composable
@TestOnly
fun ParkingSlotLayout(
    uiState: ParkingSlotUiState,
    alertState: AppAlertState,
    onOccupyClicked: (LocalDateTime) -> Unit,
    onIncrementOccupationClicked: (LocalDateTime) -> Unit,
    onFreeClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onRefreshClicked: () -> Unit
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    alertState.Bind(scaffoldState.snackbarHostState)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.screen_title_parking_slot)) },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.Rounded.ArrowBack, stringResource(R.string.go_back_cta))
                    }
                },
                actions = {
                    IconButton(
                        onClick = onRefreshClicked,
                        enabled = !uiState.loading
                    ) {
                        Icon(Icons.Default.Refresh, stringResource(R.string.refresh_cta))
                    }
                }
            )
        },
    ) { paddingValues ->
        if (!uiState.loading && uiState.parkingSlot != null) {
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(
                        24.dp,
                    )
                ) {
                    IdRow(uiState.parkingSlot.id)
                    PositionRow(uiState.parkingSlot.position)
                    StateRow(uiState.parkingSlot.state)
                    Spacer(modifier = Modifier.height(16.dp))

                    val parkingSlotState = uiState.parkingSlot.state
                    val minDatePickerDate = minDatePickerDate(parkingSlotState)
                    val defaultDatePickerDate = defaultDatePickerDate(parkingSlotState)
                    when (parkingSlotState) {
                        is ParkingSlotState.Occupied ->
                            if (parkingSlotState.currentUser) {
                                IncrementOccupationButton(
                                    minDate = minDatePickerDate,
                                    defaultDate = defaultDatePickerDate,
                                    onIncrementOccupationClicked = onIncrementOccupationClicked
                                )
                                FreeButton(
                                    onFreeClicked = onFreeClicked
                                )
                            }
                        ParkingSlotState.Free -> {
                            OccupyButton(
                                minDate = minDatePickerDate,
                                defaultDate = defaultDatePickerDate,
                                onOccupyClicked = onOccupyClicked
                            )
                        }
                    }
                }
            }
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(modifier = Modifier.size(32.dp))
            }
        }
    }
}

@Composable
private fun FreeButton(
    onFreeClicked: () -> Unit,
) {
    Button(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
        onClick = onFreeClicked,
    ) {
        Text(
            text = stringResource(R.string.free_parking_slot_cta),
            color = Color.White
        )
    }
}

@Composable
private fun IncrementOccupationButton(
    minDate: Instant,
    defaultDate: Instant,
    onIncrementOccupationClicked: (LocalDateTime) -> Unit,
) {
    val context = LocalContext.current
    Button(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        onClick = {
            showDatePicker(
                context = context,
                minDate = minDate,
                defaultDate = defaultDate,
                callback = onIncrementOccupationClicked,
            )
        },
    ) {
        Text(text = stringResource(R.string.increment_parking_slot_occupation_cta))
    }
}

@Composable
private fun OccupyButton(
    minDate: Instant,
    defaultDate: Instant,
    onOccupyClicked: (LocalDateTime) -> Unit,
) {
    val context = LocalContext.current
    Button(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        onClick = {
            showDatePicker(
                context = context,
                minDate = minDate,
                defaultDate = defaultDate,
                callback = onOccupyClicked,
            )
        },
    ) {
        Text(text = stringResource(R.string.occupy_parking_slot_cta))
    }
}

@Composable
private fun IdRow(id: String) {
    InfoRow(
        key = stringResource(R.string.parking_slot_id),
        value = id
    )
}

@Composable
private fun PositionRow(
    position: GeoPosition
) {
    val latitude = Location.convert(position.latitude, Location.FORMAT_DEGREES)
    val longitude = Location.convert(position.longitude, Location.FORMAT_DEGREES)
    InfoRow(
        key = stringResource(R.string.parking_slot_position),
        value = "$latitude, $longitude"
    )
}

@Composable
private fun StateRow(
    state: ParkingSlotState
) {
    val value = when (state) {
        is ParkingSlotState.Occupied ->
            stringResource(
                R.string.parking_slot_state_occupied,
                state.freesAt.formatted(FormatStyle.MEDIUM)
            )
        ParkingSlotState.Free ->
            stringResource(R.string.parking_slot_state_free)
    }
    InfoRow(
        key = stringResource(R.string.parking_slot_state),
        value = value
    )
}

@Composable
private fun InfoRow(
    key: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = key,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(end = 8.dp)
                .alignByBaseline(),
        )
        Text(
            text = value,
            fontSize = 18.sp,
            modifier = Modifier.alignByBaseline(),
            textAlign = TextAlign.End
        )
    }
}

private fun showDatePicker(
    context: Context,
    minDate: Instant,
    defaultDate: Instant,
    callback: (LocalDateTime) -> Unit
) {
    val localDefaultDate = defaultDate.toLocalDateTime(TimeZone.currentSystemDefault())
    val year = localDefaultDate.year
    val month = localDefaultDate.monthNumber - 1
    val dayOfMonth = localDefaultDate.dayOfMonth
    val hour = localDefaultDate.hour
    val minute = localDefaultDate.minute

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            val timePicker = TimePickerDialog(
                context,
                { _, selectedHour: Int, selectedMinute: Int ->
                    callback(
                        LocalDateTime(
                            year = selectedYear,
                            monthNumber = selectedMonth + 1,
                            dayOfMonth = selectedDayOfMonth,
                            hour = selectedHour,
                            minute = selectedMinute
                        )
                    )
                },
                hour,
                minute,
                true
            )
            timePicker.show()
        },
        year,
        month,
        dayOfMonth
    )
    datePicker.datePicker.minDate = minDate.toEpochMilliseconds()
    datePicker.show()
}

private fun minDatePickerDate(parkingSlotState: ParkingSlotState): Instant =
    when (parkingSlotState) {
        ParkingSlotState.Free ->
            Clock.System.now()
        is ParkingSlotState.Occupied ->
            parkingSlotState.freesAt
    }

private fun defaultDatePickerDate(parkingSlotState: ParkingSlotState): Instant =
    when (parkingSlotState) {
        ParkingSlotState.Free ->
            Clock.System.now().plus(1.hours)
        is ParkingSlotState.Occupied ->
            parkingSlotState.freesAt.plus(1.hours)
    }
