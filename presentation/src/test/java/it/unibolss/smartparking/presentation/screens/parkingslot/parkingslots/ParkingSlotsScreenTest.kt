package it.unibolss.smartparking.presentation.screens.parkingslot.parkingslots

import android.Manifest
import android.app.Application
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import io.mockk.MockKAnnotations
import it.unibolss.smartparking.presentation.R
import it.unibolss.smartparking.presentation.common.appalert.AppAlertState
import it.unibolss.smartparking.presentation.screens.parkingslot.parkingslots.ParkingSlotsLayout
import it.unibolss.smartparking.presentation.screens.parkingslot.parkingslots.ParkingSlotsUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowApplication

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class ParkingSlotsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val requestPermissionLabel: String
        get() = composeTestRule.activity.getString(R.string.request_permission_cta)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testLoadingUIValid() {
        val application: Application = ApplicationProvider.getApplicationContext()
        val app: ShadowApplication = Shadows.shadowOf(application)
        app.grantPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
        // Unfortunately currently it is not possible to test location with roboelectric in
        // jetpack compose
        composeTestRule.setContent {
            ParkingSlotsLayout(
                uiState = ParkingSlotsUiState.initial(),
                alertState = AppAlertState.None,
                onMapMoved = {},
                onParkingSlotClicked = {},
                onChangePasswordClicked = {},
                onLogoutClicked = {},
                onDeleteUserClicked = {},
                onVisibilityChanged = {}
            )
        }

        composeTestRule
            .onAllNodes(hasText(requestPermissionLabel))
            .assertCountEquals(0)
    }

    @Test
    fun testMissingPermissions() {
        composeTestRule.setContent {
            ParkingSlotsLayout(
                uiState = ParkingSlotsUiState.initial(),
                alertState = AppAlertState.None,
                onMapMoved = {},
                onParkingSlotClicked = {},
                onChangePasswordClicked = {},
                onLogoutClicked = {},
                onDeleteUserClicked = {},
                onVisibilityChanged = {}
            )
        }
        composeTestRule
            .onNode(hasText(requestPermissionLabel))
            .assertIsDisplayed()
            .assertIsEnabled()
    }
}
