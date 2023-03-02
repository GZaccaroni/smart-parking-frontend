package it.unibolss.smartparking.presentation.screens.parkingslots

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import it.unibolss.smartparking.domain.entities.geo.GeoPosition
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlotState
import it.unibolss.smartparking.presentation.R
import it.unibolss.smartparking.presentation.common.appalert.AppAlertState
import it.unibolss.smartparking.presentation.common.appalert.Bind
import it.unibolss.smartparking.presentation.common.date.formatted
import it.unibolss.smartparking.presentation.common.ui.onVisibilityChanged
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import org.jetbrains.annotations.TestOnly
import org.koin.androidx.compose.koinViewModel
import java.time.format.FormatStyle

@Composable
fun ParkingSlotsScreen(
    vm: ParkingSlotsScreenViewModel = koinViewModel()
) {
    val uiState by vm.uiState.collectAsState()
    val alertState by vm.alertState.collectAsState()
    ParkingSlotsLayout(
        uiState = uiState,
        alertState = alertState,
        onMapMoved = { vm.loadParkingSlots(it) },
        onParkingSlotClicked = { vm.viewParkingSlot(it) },
        onChangePasswordClicked = { vm.changePassword() },
        onLogoutClicked = { vm.logout() },
        onDeleteUserClicked = { vm.deleteUser() },
        onVisibilityChanged = {
            vm.visibilityChanged(it)
        }
    )
}

@Composable
@TestOnly
fun ParkingSlotsLayout(
    uiState: ParkingSlotsUiState,
    alertState: AppAlertState,
    onMapMoved: (GeoPosition) -> Unit,
    onParkingSlotClicked: (ParkingSlot) -> Unit,
    onChangePasswordClicked: () -> Unit,
    onLogoutClicked: () -> Unit,
    onDeleteUserClicked: () -> Unit,
    onVisibilityChanged: (Boolean) -> Unit
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    alertState.Bind(scaffoldState.snackbarHostState)

    var showMenu by remember { mutableStateOf(false) }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.onVisibilityChanged(onVisibilityChanged),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.screen_title_parking_slots)) },
                actions = {
                    if (uiState.currentParkingSlot != null) {
                        IconButton(
                            enabled = !uiState.loading,
                            onClick = {
                                onParkingSlotClicked(uiState.currentParkingSlot)
                            }
                        ) {
                            Icon(
                                Icons.Default.Place,
                                contentDescription = stringResource(R.string.current_parking_slot_cta)
                            )
                        }
                    }
                    IconButton(
                        enabled = !uiState.loading,
                        onClick = { showMenu = !showMenu },
                    ) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.more_cta)
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu && !uiState.loading,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(onClick = onChangePasswordClicked) {
                            Icon(Icons.Filled.Lock, contentDescription = null)
                            Text(stringResource(R.string.change_password_cta))
                        }
                        DropdownMenuItem(onClick = onLogoutClicked) {
                            Icon(Icons.Filled.ExitToApp, contentDescription = null)
                            Text(stringResource(R.string.logout_cta))
                        }
                        DropdownMenuItem(onClick = onDeleteUserClicked) {
                            Icon(Icons.Filled.Delete, contentDescription = null)
                            Text(stringResource(R.string.delete_user_cta))
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        if (!uiState.loading) {
            Box(
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(
                        LatLng(44.216036, 12.049972),
                        19f
                    )
                }
                if (!cameraPositionState.isMoving) {
                    onMapMoved(
                        GeoPosition(
                            longitude = cameraPositionState.position.target.longitude,
                            latitude = cameraPositionState.position.target.latitude,
                        )
                    )
                }
                GoogleMap(
                    cameraPositionState = cameraPositionState,
                    modifier = Modifier.matchParentSize(),
                    properties = MapProperties(
                        mapType = MapType.NORMAL,
                        minZoomPreference = 17f
                    ),
                    uiSettings = MapUiSettings(
                        indoorLevelPickerEnabled = false,
                        mapToolbarEnabled = false,
                    ),
                ) {
                    uiState.parkingSlots.map { parkingSlot ->
                        val parkingSlotState = parkingSlot.state
                        val markerHue = when (parkingSlotState) {
                            ParkingSlotState.Free -> BitmapDescriptorFactory.HUE_GREEN
                            is ParkingSlotState.Occupied ->
                                if (parkingSlotState.freesAt > Clock.System.now().plus(
                                        DateTimePeriod(minutes = 10),
                                        TimeZone.UTC
                                    )
                                ) {
                                    BitmapDescriptorFactory.HUE_RED
                                } else {
                                    BitmapDescriptorFactory.HUE_ORANGE
                                }
                        }
                        val snippet = when (parkingSlotState) {
                            is ParkingSlotState.Occupied ->
                                stringResource(
                                    R.string.parking_slot_state_occupied,
                                    parkingSlotState.freesAt.formatted(FormatStyle.MEDIUM)
                                )
                            ParkingSlotState.Free ->
                                stringResource(R.string.parking_slot_state_free)
                        }
                        Marker(
                            state = MarkerState(
                                position = LatLng(parkingSlot.position.latitude, parkingSlot.position.longitude)
                            ),
                            title = parkingSlot.id,
                            icon = BitmapDescriptorFactory.defaultMarker(markerHue),
                            snippet = snippet,
                            onInfoWindowClick = {
                                onParkingSlotClicked(parkingSlot)
                            }
                        )
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
