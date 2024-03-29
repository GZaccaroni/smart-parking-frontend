package it.unibolss.smartparking.presentation.screens.parkingslot.parkingslot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlotState
import it.unibolss.smartparking.domain.usecases.parkingslot.FreeParkingSlot
import it.unibolss.smartparking.domain.usecases.parkingslot.IncrementParkingSlotOccupation
import it.unibolss.smartparking.domain.usecases.parkingslot.OccupyParkingSlot
import it.unibolss.smartparking.domain.usecases.parkingslot.ViewParkingSlot
import it.unibolss.smartparking.presentation.R
import it.unibolss.smartparking.presentation.common.appalert.AppAlert
import it.unibolss.smartparking.presentation.common.appalert.AppAlertState
import it.unibolss.smartparking.presentation.common.appalert.show
import it.unibolss.smartparking.presentation.common.error.handleAppError
import it.unibolss.smartparking.presentation.navigation.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class ParkingSlotScreenViewModel(
    private val parkingSlotId: String,
    private val viewParkingSlot: ViewParkingSlot,
    private val occupyParkingSlot: OccupyParkingSlot,
    private val incrementParkingSlotOccupation: IncrementParkingSlotOccupation,
    private val freeParkingSlot: FreeParkingSlot,
    private val router: Router,
) : ViewModel() {

    private val _alertState = MutableStateFlow<AppAlertState>(AppAlertState.None)

    /**
     * Current state of UI alerts
     */
    val alertState: StateFlow<AppAlertState> = _alertState.asStateFlow()

    private val _uiState = MutableStateFlow(ParkingSlotUiState.initial())

    /**
     * Current state of the UI
     */
    val uiState: StateFlow<ParkingSlotUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    /**
     * Increments the occupation of the current parking slot
     * @throws IllegalStateException if [uiState] is loading ([ParkingSlotUiState.loading])
     * @throws IllegalStateException if [uiState] has no parking slot set ([ParkingSlotUiState.parkingSlot])
     * @throws IllegalStateException if [uiState] parking slot ([ParkingSlotUiState.parkingSlot]) is not free
     */
    fun occupy(stopEnd: LocalDateTime) {
        val currentUiState = _uiState.value
        check(!currentUiState.loading) {
            "Occupy method should not be called if loading is true"
        }
        val parkingSlot = currentUiState.parkingSlot
        check(parkingSlot != null) {
            "You should await parking slot to be loaded before calling occupy on it"
        }
        check(parkingSlot.isFree()) {
            "Occupy can be called only on a free parking slot"
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)
            val stopEndInstant = stopEnd.toInstant(TimeZone.currentSystemDefault())
            val result = occupyParkingSlot(
                OccupyParkingSlot.Params(
                    id = parkingSlotId,
                    stopEnd = stopEndInstant
                )
            )
            when (result) {
                is Either.Left -> {
                    _uiState.value = _uiState.value.copy(
                        loading = false
                    )
                    handleAppError(result.value, _alertState, router)
                }
                is Either.Right -> {
                    _uiState.value = _uiState.value.copy(
                        parkingSlot = parkingSlot.copy(
                            state = ParkingSlotState.Occupied(freesAt = stopEndInstant, currentUser = true)
                        ),
                        loading = false
                    )
                    _alertState.show(AppAlert.Text(R.string.app_success_parking_slot_occupy))
                }
            }
        }
    }

    /**
     * Increments the occupation of the current parking slot
     * @throws IllegalStateException if [uiState] is loading ([ParkingSlotUiState.loading])
     * @throws IllegalStateException if [uiState] has no parking slot set ([ParkingSlotUiState.parkingSlot])
     */
    fun incrementOccupation(stopEnd: LocalDateTime) {
        val currentUiState = _uiState.value
        check(!currentUiState.loading) {
            "Increment Occupation method should not be called if loading is true"
        }
        val parkingSlot = currentUiState.parkingSlot
        check(parkingSlot != null) {
            "You should await parking slot to be loaded before calling increment occupation on it"
        }
        when (val state = parkingSlot.state) {
            ParkingSlotState.Free ->
                error("Increment Occupation can be called only on a occupied parking slot")
            is ParkingSlotState.Occupied ->
                if (!state.currentUser) {
                    error(
                        "Increment Occupation can be called only on a parking slot occupied by current user"
                    )
                }
        }
        viewModelScope.launch {
            val stopEndInstant = stopEnd.toInstant(TimeZone.currentSystemDefault())
            _uiState.value = _uiState.value.copy(loading = true)
            val result = incrementParkingSlotOccupation(
                IncrementParkingSlotOccupation.Params(
                    stopEnd = stopEndInstant
                )
            )
            when (result) {
                is Either.Left -> {
                    _uiState.value = _uiState.value.copy(
                        loading = false
                    )
                    handleAppError(result.value, _alertState, router)
                }
                is Either.Right -> {
                    _uiState.value = _uiState.value.copy(
                        parkingSlot = parkingSlot.copy(
                            state = ParkingSlotState.Occupied(freesAt = stopEndInstant, currentUser = true)
                        ),
                        loading = false
                    )
                    _alertState.show(AppAlert.Text(R.string.app_success_parking_slot_increment_occupation))
                }
            }
        }
    }

    /**
     * Frees the shown parking slot
     * @throws IllegalStateException if [uiState] is loading ([ParkingSlotUiState.loading])
     * @throws IllegalStateException if [uiState] has no parking slot set ([ParkingSlotUiState.parkingSlot])
     */
    fun free() {
        val currentUiState = _uiState.value
        check(!currentUiState.loading) {
            "Free method should not be called if loading is true"
        }
        val parkingSlot = currentUiState.parkingSlot
        check(parkingSlot != null) {
            "You should await parking slot to be loaded before calling free on it"
        }
        when (val state = parkingSlot.state) {
            ParkingSlotState.Free ->
                error("Free can be called only on a occupied parking slot")
            is ParkingSlotState.Occupied ->
                if (!state.currentUser) {
                    error("Free can be called only on a parking slot occupied by current user")
                }
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)
            val result = freeParkingSlot(Unit)
            when (result) {
                is Either.Left -> {
                    _uiState.value = _uiState.value.copy(
                        loading = false
                    )
                    handleAppError(result.value, _alertState, router)
                }
                is Either.Right -> {
                    _uiState.value = _uiState.value.copy(
                        parkingSlot = parkingSlot.copy(
                            state = ParkingSlotState.Free
                        ),
                        loading = false
                    )
                    _alertState.show(AppAlert.Text(R.string.app_success_parking_slot_free))
                }
            }
        }
    }

    /**
     * Refreshes the details of the parking slot
     * @throws IllegalStateException if [uiState] is loading ([ParkingSlotUiState.loading])
     */
    fun refresh() {
        check(!_uiState.value.loading) {
            "Refresh should not be called if view model is already loading"
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)
            val result = viewParkingSlot(ViewParkingSlot.Params(parkingSlotId))

            when (result) {
                is Either.Right -> {
                    _uiState.value = _uiState.value.copy(loading = false, parkingSlot = result.value)
                }
                is Either.Left -> {
                    _uiState.value = _uiState.value.copy(loading = false)
                    handleAppError(result.value, _alertState, router)
                    router.popBackStack()
                }
            }
        }
    }

    /**
     * Goes back to the previous screen
     * @throws IllegalStateException if [uiState] is loading ([ParkingSlotUiState.loading])
     */
    fun goBack() {
        check(!uiState.value.loading) {
            "goBack method should not be called if loading is true"
        }
        router.popBackStack()
    }
}
