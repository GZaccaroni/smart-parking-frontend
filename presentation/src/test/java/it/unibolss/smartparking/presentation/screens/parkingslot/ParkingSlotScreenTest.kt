package it.unibolss.smartparking.presentation.screens.parkingslot

import androidx.activity.ComponentActivity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import io.mockk.MockKAnnotations
import it.unibolss.smartparking.common.withRole
import it.unibolss.smartparking.domain.entities.geo.GeoPosition
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlotState
import it.unibolss.smartparking.presentation.R
import it.unibolss.smartparking.presentation.common.appalert.AppAlertState
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.datetime.Instant
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.concurrent.atomic.AtomicBoolean

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class ParkingSlotScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val idLabel: String
        get() = composeTestRule.activity.getString(R.string.parking_slot_id)
    private val positionLabel: String
        get() = composeTestRule.activity.getString(R.string.parking_slot_position)
    private val stateLabel: String
        get() = composeTestRule.activity.getString(R.string.parking_slot_state)
    private val occupyLabel: String
        get() = composeTestRule.activity.getString(R.string.occupy_parking_slot_cta)
    private val incrementOccupationLabel: String
        get() = composeTestRule.activity.getString(R.string.increment_parking_slot_occupation_cta)
    private val freeLabel: String
        get() = composeTestRule.activity.getString(R.string.free_parking_slot_cta)
    private val refreshLabel: String
        get() = composeTestRule.activity.getString(R.string.refresh_cta)
    private val goBackLabel: String
        get() = composeTestRule.activity.getString(R.string.go_back_cta)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testLoadingUIValid() {
        val onBackCalled = AtomicBoolean(false)
        composeTestRule.setContent {
            ParkingSlotLayout(
                uiState = ParkingSlotUiState(loading = true, parkingSlot = null),
                alertState = AppAlertState.None,
                onOccupyClicked = { },
                onIncrementOccupationClicked = { },
                onFreeClicked = { },
                onRefreshClicked = { },
                onBackClicked = {
                    onBackCalled.set(true)
                },
            )
        }
        val missingNodesTexts =
            listOf(idLabel, positionLabel, stateLabel, occupyLabel, incrementOccupationLabel, freeLabel)

        missingNodesTexts.forEach {
            composeTestRule
                .onAllNodes(hasText(it))
                .assertCountEquals(0)
        }
        composeTestRule
            .onNode(hasContentDescription(refreshLabel))
            .assertIsDisplayed()
            .assertIsNotEnabled()
        composeTestRule
            .onNode(hasContentDescription(goBackLabel))
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()
        assertTrue(onBackCalled.get())
    }

    @Test
    fun testOccupiedCurrentParkingSlotUIValid() {
        val onFreeCalled = AtomicBoolean(false)
        val onRefreshCalled = AtomicBoolean(false)
        val onBackCalled = AtomicBoolean(false)
        composeTestRule.setContent {
            ParkingSlotLayout(
                uiState = ParkingSlotUiState(
                    loading = false,
                    parkingSlot = ParkingSlot(
                        id = "test",
                        state = ParkingSlotState.Occupied(
                            freesAt = Instant.DISTANT_FUTURE,
                            currentUser = true,
                        ),
                        position = GeoPosition(0.0, 0.0)
                    )
                ),
                alertState = AppAlertState.None,
                onOccupyClicked = { },
                onIncrementOccupationClicked = { },
                onFreeClicked = {
                    onFreeCalled.set(true)
                },
                onRefreshClicked = {
                    onRefreshCalled.set(true)
                },
                onBackClicked = {
                    onBackCalled.set(true)
                },
            )
        }
        assertParkingSlotCommon()
        composeTestRule
            .onAllNodes(withRole(Role.Button).and(hasText(occupyLabel)))
            .assertCountEquals(0)
        composeTestRule
            .onNode(withRole(Role.Button).and(hasText(incrementOccupationLabel)))
            .performScrollTo()
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()
        composeTestRule
            .onNode(withRole(Role.Button).and(hasText(freeLabel)))
            .performScrollTo()
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()
        assertTrue(onFreeCalled.get())
        assertTrue(onRefreshCalled.get())
        assertTrue(onBackCalled.get())
    }

    @Test
    fun testOccupiedParkingSlotUIValid() {
        val onRefreshCalled = AtomicBoolean(false)
        val onBackCalled = AtomicBoolean(false)
        composeTestRule.setContent {
            ParkingSlotLayout(
                uiState = ParkingSlotUiState(
                    loading = false,
                    parkingSlot = ParkingSlot(
                        id = "test",
                        state = ParkingSlotState.Occupied(
                            freesAt = Instant.DISTANT_FUTURE,
                            currentUser = false,
                        ),
                        position = GeoPosition(0.0, 0.0)
                    )
                ),
                alertState = AppAlertState.None,
                onOccupyClicked = { },
                onIncrementOccupationClicked = { },
                onFreeClicked = { },
                onRefreshClicked = {
                    onRefreshCalled.set(true)
                },
                onBackClicked = {
                    onBackCalled.set(true)
                },
            )
        }
        assertParkingSlotCommon()
        composeTestRule
            .onAllNodes(withRole(Role.Button).and(hasText(occupyLabel)))
            .assertCountEquals(0)
        composeTestRule
            .onAllNodes(withRole(Role.Button).and(hasText(incrementOccupationLabel)))
            .assertCountEquals(0)
        composeTestRule
            .onAllNodes(withRole(Role.Button).and(hasText(freeLabel)))
            .assertCountEquals(0)
        assertTrue(onRefreshCalled.get())
        assertTrue(onBackCalled.get())
    }

    @Test
    fun testFreeParkingSlotUIValid() {
        val onRefreshCalled = AtomicBoolean(false)
        val onBackCalled = AtomicBoolean(false)
        composeTestRule.setContent {
            ParkingSlotLayout(
                uiState = ParkingSlotUiState(
                    loading = false,
                    parkingSlot = ParkingSlot(
                        id = "test",
                        state = ParkingSlotState.Free,
                        position = GeoPosition(0.0, 0.0)
                    )
                ),
                alertState = AppAlertState.None,
                onOccupyClicked = { },
                onIncrementOccupationClicked = { },
                onFreeClicked = { },
                onRefreshClicked = {
                    onRefreshCalled.set(true)
                },
                onBackClicked = {
                    onBackCalled.set(true)
                },
            )
        }
        assertParkingSlotCommon()
        composeTestRule
            .onNode(withRole(Role.Button).and(hasText(occupyLabel)))
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()
        composeTestRule
            .onAllNodes(withRole(Role.Button).and(hasText(incrementOccupationLabel)))
            .assertCountEquals(0)
        composeTestRule
            .onAllNodes(withRole(Role.Button).and(hasText(freeLabel)))
            .assertCountEquals(0)
        assertTrue(onRefreshCalled.get())
        assertTrue(onBackCalled.get())
    }

    private fun assertParkingSlotCommon() {
        composeTestRule
            .onNode(hasContentDescription(goBackLabel))
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()
        composeTestRule
            .onNode(hasContentDescription(refreshLabel))
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()
        composeTestRule
            .onNode(hasText(idLabel))
            .assertIsDisplayed()
        composeTestRule
            .onNode(hasText(positionLabel))
            .assertIsDisplayed()
        composeTestRule
            .onNode(hasText(stateLabel))
            .assertIsDisplayed()
    }
}
