package it.unibolss.smartparking.presentation.screens.parkingslot.parkingslots

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
import it.unibolss.smartparking.presentation.common.error.handleAppError
import it.unibolss.smartparking.presentation.common.flow.tickerFlow
import it.unibolss.smartparking.presentation.common.position.distanceFrom
import it.unibolss.smartparking.presentation.navigation.Router
import it.unibolss.smartparking.presentation.screens.parkingslot.parkingslot.ParkingSlotRoute
import it.unibolss.smartparking.presentation.screens.user.changepassword.ChangePasswordRoute
import it.unibolss.smartparking.presentation.screens.user.login.LoginRoute
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * View Model of the
 */
@OptIn(ObsoleteCoroutinesApi::class)
class ParkingSlotsScreenViewModel(
    private val viewCurrentParkingSlot: ViewCurrentParkingSlot,
    private val findParkingSlots: FindParkingSlots,
    private val logoutUser: LogoutUser,
    private val deleteUser: DeleteUser,
    private val router: Router,
) : ViewModel() {

    private val _alertState = MutableStateFlow<AppAlertState>(AppAlertState.None)

    /**
     * Current state of UI alerts
     */
    val alertState: StateFlow<AppAlertState> = _alertState.asStateFlow()

    private val _uiState = MutableStateFlow(ParkingSlotsUiState.initial())

    /**
     * Current state of the UI
     */
    val uiState: StateFlow<ParkingSlotsUiState> = _uiState.asStateFlow()

    /**
     * Navigates to the screen with the details of the [parkingSlot]
     * @throws IllegalStateException if [uiState] is loading ([ParkingSlotsUiState.loading])
     */
    fun viewParkingSlot(parkingSlot: ParkingSlot) {
        check(!_uiState.value.loading) {
            "viewParkingSlot method should not be called if loading is true"
        }
        router.navigateTo(ParkingSlotRoute(parkingSlot.id))
    }

    /**
     * Navigates to the change password screen
     * @throws IllegalStateException if [uiState] is loading ([ParkingSlotsUiState.loading])
     */
    fun changePassword() {
        check(!_uiState.value.loading) {
            "changePassword method should not be called if loading is true"
        }
        router.navigateTo(ChangePasswordRoute)
    }

    /**
     * Logs out the currently logged in user
     * @throws IllegalStateException if [uiState] is loading ([ParkingSlotsUiState.loading])
     */
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
                    handleAppError(result.value, _alertState, router)
                }
                is Either.Right -> {
                    router.navigateTo(
                        LoginRoute,
                        NavOptions.Builder().setPopUpTo(0, true).build(),
                    )
                    _alertState.show(AppAlert.Text(R.string.app_success_logout_user))
                }
            }
        }
    }

    /**
     * Deletes the currently logged in user
     * @throws IllegalStateException if [uiState] is loading ([ParkingSlotsUiState.loading])
     */
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
                    router.navigateTo(
                        LoginRoute,
                        NavOptions.Builder().setPopUpTo(0, true).build(),
                    )
                    _alertState.show(AppAlert.Text(R.string.app_success_delete_user))
                }
            }
        }
    }

    private var latestPositionRequested: GeoPosition? = null
    private var loadParkingSlotsJob: Job? = null

    /**
     * Loads the data of all the parking slot near [position]
     */
    fun loadParkingSlots(position: GeoPosition) {
        val curLatestPositionRequested = this.latestPositionRequested
        if (curLatestPositionRequested != null &&
            curLatestPositionRequested.distanceFrom(position) < FIND_PARKING_SLOTS_TOLERANCE
        ) {
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
                when (result) {
                    is Either.Right -> {
                        _uiState.value = _uiState.value.copy(
                            loading = false,
                            parkingSlots = result.value
                        )
                    }
                    is Either.Left -> {
                        latestPositionRequested = null
                        handleAppError(result.value, _alertState, router)
                    }
                }
            }.collect()
        }
    }

    /**
     * Informs the view model that the visibility of the view is changed
     */
    fun visibilityChanged(visible: Boolean) {
        if (visible) {
            loadCurrentParkingSlot()
        } else {
            cancelParkingSlotRefreshJob()
        }
    }

    private fun loadCurrentParkingSlot() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)
            val result = viewCurrentParkingSlot(Unit)

            when (result) {
                is Either.Right -> {
                    _uiState.value = _uiState.value.copy(
                        currentParkingSlot = result.value,
                        loading = false
                    )
                }
                is Either.Left -> {
                    _uiState.value = _uiState.value.copy(
                        loading = false,
                    )
                    handleAppError(result.value, _alertState, router)
                }
            }
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
