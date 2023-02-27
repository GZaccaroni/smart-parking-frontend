package it.unibolss.smartparking.presentation.screens.changepassword

import androidx.activity.ComponentActivity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import io.mockk.MockKAnnotations
import it.unibolss.smartparking.common.withRole
import it.unibolss.smartparking.presentation.R
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.concurrent.atomic.AtomicBoolean

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class ChangePasswordScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val currentPasswordLabel: String
        get() = composeTestRule.activity.getString(R.string.user_current_password)
    private val newPasswordLabel: String
        get() = composeTestRule.activity.getString(R.string.user_new_password)
    private val submitLabel: String
        get() = composeTestRule.activity.getString(R.string.change_password_cta)
    private val goBackLabel: String
        get() = composeTestRule.activity.getString(R.string.go_back_cta)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testUIValid() {
        val onCurrentPasswordChangeCalled = AtomicBoolean(false)
        val onNewPasswordChangeCalled = AtomicBoolean(false)
        val onSubmitCalled = AtomicBoolean(false)
        val onBackCalled = AtomicBoolean(false)
        composeTestRule.setContent {
            ChangePasswordLayout(
                uiState = ChangePasswordUiState.initial(),
                onCurrentPasswordChange = { onCurrentPasswordChangeCalled.set(true) },
                onNewPasswordChange = { onNewPasswordChangeCalled.set(true) },
                onSubmit = { onSubmitCalled.set(true) },
                onBackClicked = { onBackCalled.set(true) }
            )
        }
        composeTestRule
            .onNode(hasText(currentPasswordLabel))
            .assertIsDisplayed()
            .assertIsEnabled()
            .performTextInput("currentpassword")
        composeTestRule
            .onNode(hasText(newPasswordLabel))
            .assertIsDisplayed()
            .assertIsEnabled()
            .performTextInput("newpassword")
        composeTestRule
            .onNode(withRole(Role.Button).and(hasContentDescription(goBackLabel)))
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()
        composeTestRule
            .onNode(withRole(Role.Button).and(hasText(submitLabel)))
            .assertIsDisplayed()
            .assertIsNotEnabled()
            .performClick()
        assertTrue(onNewPasswordChangeCalled.get())
        assertTrue(onCurrentPasswordChangeCalled.get())
        assertTrue(onBackCalled.get())
        assertFalse(onSubmitCalled.get())
    }

    @Test
    fun testIsSubmittable() {
        val onSubmitCalled = AtomicBoolean(false)
        composeTestRule.setContent {
            ChangePasswordLayout(
                uiState = ChangePasswordUiState.initial().copy(submitEnabled = true),
                onCurrentPasswordChange = { },
                onNewPasswordChange = { },
                onSubmit = { onSubmitCalled.set(true) },
                onBackClicked = { },
            )
        }
        composeTestRule
            .onNode(withRole(Role.Button).and(hasText(submitLabel)))
            .performClick()
        assertTrue(onSubmitCalled.get())
    }
}
