package it.unibolss.smartparking.presentation.screens.parkingslots

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import arrow.core.Either
import it.unibolss.smartparking.domain.entities.geo.GeoPosition
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import it.unibolss.smartparking.domain.usecases.parkingslot.FindParkingSlots
import it.unibolss.smartparking.domain.usecases.parkingslot.ViewCurrentParkingSlot
import it.unibolss.smartparking.domain.usecases.user.DeleteUser
import it.unibolss.smartparking.domain.usecases.user.LogoutUser
import it.unibolss.smartparking.presentation.R
import it.unibolss.smartparking.presentation.common.appalert.AppAlert
import it.unibolss.smartparking.presentation.common.appalert.AppAlertState
import it.unibolss.smartparking.presentation.common.appalert.show
import it.unibolss.smartparking.presentation.common.flow.tickerFlow
import it.unibolss.smartparking.presentation.common.position.distanceFrom
import it.unibolss.smartparking.presentation.navigation.Router
import it.unibolss.smartparking.presentation.screens.changepassword.ChangePasswordRoute
import it.unibolss.smartparking.presentation.screens.login.LoginRoute
import it.unibolss.smartparking.presentation.screens.parkingslot.ParkingSlotRoute
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(ObsoleteCoroutinesApi::class)
class ParkingSlotsScreenViewModel(
    private val viewCurrentParkingSlot: ViewCurrentParkingSlot,
    private val findParkingSlots: FindParkingSlots,
    private val logoutUser: LogoutUser,
    private val deleteUser: DeleteUser,
    private val router: Router,
) : ViewModel() {

    private val _alertState = MutableStateFlow<AppAlertState>(AppAlertState.None)
    val alertState: StateFlow<AppAlertState> = _alertState.asStateFlow()

    private val _uiState = MutableStateFlow(ParkingSlotsUiState.initial())
    val uiState: StateFlow<ParkingSlotsUiState> = _uiState.asStateFlow()

    init {
        loadCurrentParkingSlot()
    }

    fun viewParkingSlot(parkingSlot: ParkingSlot) {
        router.navigateTo(ParkingSlotRoute(parkingSlot.id))
    }
    fun changePassword() {
        router.navigateTo(ChangePasswordRoute)
    }
    fun logout() {
        val currentUiState = _uiState.value
        check(!currentUiState.loading) {
            "Logout method should not be called if loading is true"
        }
        cancelParkingSlotRefreshJob()
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)
            val result = logoutUser(Unit)
            when (result) {
                is Either.Left -> {
                    _uiState.value = _uiState.value.copy(
                        loading = false
                    )
                    _alertState.show(AppAlert.Error(result.value))
                }
                is Either.Right -> {
                    _alertState.show(AppAlert.Text(R.string.app_success_logout_user))
                    router.navigateTo(
                        LoginRoute,
                        NavOptions.Builder().setPopUpTo(0, true).build(),
                    )
                }
            }
        }
    }
    fun deleteUser() {
        val currentUiState = _uiState.value
        check(!currentUiState.loading) {
            "Delete user method should not be called if loading is true"
        }
        cancelParkingSlotRefreshJob()
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)
            val result = deleteUser(Unit)
            when (result) {
                is Either.Left -> {
                    _uiState.value = _uiState.value.copy(
                        loading = false
                    )
                    _alertState.show(AppAlert.Error(result.value))
                }
                is Either.Right -> {
                    _alertState.show(AppAlert.Text(R.string.app_success_delete_user))
                    router.navigateTo(
                        LoginRoute,
                        NavOptions.Builder().setPopUpTo(0, true).build(),
                    )
                }
            }
        }
    }

    private var latestPositionRequested: GeoPosition? = null
    private var loadParkingSlotsJob: Job? = null

    fun loadParkingSlots(position: GeoPosition) {
        val curLatestPositionRequested = this.latestPositionRequested
        if (curLatestPositionRequested != null &&
            curLatestPositionRequested.distanceFrom(position) < FIND_PARKING_SLOTS_TOLERANCE) {
            // Ignore
            return
        }
        loadParkingSlotsJob?.cancel()
        latestPositionRequested = position

        loadParkingSlotsJob = viewModelScope.launch {
            tickerFlow(
                FIND_PARKING_SLOTS_REFRESH_INTERVAL,
                0L,
                coroutineContext
            ).onEach {
                val result = findParkingSlots(
                    FindParkingSlots.Params(
                        position,
                        FIND_PARKING_SLOTS_RADIUS
                    )
                )
                result.fold(
                    {
                        _alertState.show(AppAlert.Error(it))
                        latestPositionRequested = null
                    },
                    {
                        _uiState.value = _uiState.value.copy(
                            loading = false,
                            parkingSlots = it
                        )
                    }
                )
            }.collect()
        }
    }

    fun visibilityChanged(visible: Boolean) {
        if (!visible) {
            cancelParkingSlotRefreshJob()
        }
    }

    private fun loadCurrentParkingSlot() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)
            val result = viewCurrentParkingSlot(Unit)
            result.fold(
                {
                    _alertState.show(AppAlert.Error(it))
                    router.popBackStack()
                },
                {
                    _uiState.value = _uiState.value.copy(
                        loading = false,
                        currentParkingSlot = it,
                    )
                }
            )
        }
    }
    private fun cancelParkingSlotRefreshJob() {
        loadParkingSlotsJob?.cancel()
        latestPositionRequested = null
    }

    companion object {
        private const val FIND_PARKING_SLOTS_TOLERANCE = 10.0 // Meters
        private const val FIND_PARKING_SLOTS_RADIUS = 500.0 // Meters
        private const val FIND_PARKING_SLOTS_REFRESH_INTERVAL = 10000L // Millis
    }
}
