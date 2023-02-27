package it.unibolss.smartparking.presentation.screens.login

import androidx.activity.ComponentActivity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import io.mockk.MockKAnnotations
import it.unibolss.smartparking.common.withRole
import it.unibolss.smartparking.presentation.R
import it.unibolss.smartparking.presentation.common.appalert.AppAlertState
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
class LoginScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val emailLabel: String
        get() = composeTestRule.activity.getString(R.string.user_email)
    private val passwordLabel: String
        get() = composeTestRule.activity.getString(R.string.user_password)
    private val submitLabel: String
        get() = composeTestRule.activity.getString(R.string.login_cta)
    private val signUpLabel: String
        get() = composeTestRule.activity.getString(R.string.go_to_sign_up_cta)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testUIValid() {
        val onPasswordChangeCalled = AtomicBoolean(false)
        val onEmailChangeCalled = AtomicBoolean(false)
        val onSubmitCalled = AtomicBoolean(false)
        val onSignUpCalled = AtomicBoolean(false)
        composeTestRule.setContent {
            LoginLayout(
                uiState = LoginUiState.initial(),
                alertState = AppAlertState.None,
                onEmailChange = { onEmailChangeCalled.set(true) },
                onPasswordChange = { onPasswordChangeCalled.set(true) },
                onSubmit = { onSubmitCalled.set(true) },
                onSignUpClick = { onSignUpCalled.set(true) }
            )
        }
        composeTestRule
            .onNode(hasText(emailLabel))
            .assertIsDisplayed()
            .assertIsEnabled()
            .performTextInput("mail")
        composeTestRule
            .onNode(hasText(passwordLabel))
            .assertIsDisplayed()
            .assertIsEnabled()
            .performTextInput("pw")
        composeTestRule
            .onNode(withRole(Role.Button).and(hasText(signUpLabel)))
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()
        composeTestRule
            .onNode(withRole(Role.Button).and(hasText(submitLabel)))
            .assertIsDisplayed()
            .assertIsNotEnabled()
            .performClick()

        assertTrue(onPasswordChangeCalled.get())
        assertTrue(onEmailChangeCalled.get())
        assertTrue(onSignUpCalled.get())

        assertFalse(onSubmitCalled.get())
    }

    @Test
    fun testIsSubmittable() {
        val onSubmitCalled = AtomicBoolean(false)
        composeTestRule.setContent {
            LoginLayout(
                uiState = LoginUiState.initial().copy(submitEnabled = true),
                alertState = AppAlertState.None,
                onEmailChange = { },
                onPasswordChange = { },
                onSubmit = { onSubmitCalled.set(true) },
                onSignUpClick = {}
            )
        }
        composeTestRule
            .onNode(withRole(Role.Button).and(hasText(submitLabel)))
            .performClick()
        assertTrue(onSubmitCalled.get())
    }
}
