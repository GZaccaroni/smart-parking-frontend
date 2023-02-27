package it.unibolss.smartparking.presentation.screens.signup

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
class SignUpScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val nameLabel: String
        get() = composeTestRule.activity.getString(R.string.user_name)
    private val emailLabel: String
        get() = composeTestRule.activity.getString(R.string.user_email)
    private val passwordLabel: String
        get() = composeTestRule.activity.getString(R.string.user_password)
    private val submitLabel: String
        get() = composeTestRule.activity.getString(R.string.sign_up_cta)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testUIValid() {
        val onNameChangeCalled = AtomicBoolean(false)
        val onPasswordChangeCalled = AtomicBoolean(false)
        val onEmailChangeCalled = AtomicBoolean(false)
        val onSubmitCalled = AtomicBoolean(false)
        composeTestRule.setContent {
            SignUpLayout(
                uiState = SignUpUiState.initial(),
                alertState = AppAlertState.None,
                onNameChange = { onNameChangeCalled.set(true) },
                onEmailChange = { onEmailChangeCalled.set(true) },
                onPasswordChange = { onPasswordChangeCalled.set(true) },
                onSubmit = { onSubmitCalled.set(true) }
            )
        }
        composeTestRule
            .onNode(hasText(nameLabel))
            .assertIsDisplayed()
            .assertIsEnabled()
            .performTextInput("name")
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
            .onNode(withRole(Role.Button).and(hasText(submitLabel)))
            .assertIsDisplayed()
            .assertIsNotEnabled()
            .performClick()
        assertTrue(onNameChangeCalled.get())
        assertTrue(onPasswordChangeCalled.get())
        assertTrue(onEmailChangeCalled.get())
        assertFalse(onSubmitCalled.get())
    }

    @Test
    fun testIsSubmittable() {
        val onSubmitCalled = AtomicBoolean(false)
        composeTestRule.setContent {
            SignUpLayout(
                uiState = SignUpUiState.initial().copy(submitEnabled = true),
                alertState = AppAlertState.None,
                onNameChange = { },
                onEmailChange = { },
                onPasswordChange = { },
                onSubmit = { onSubmitCalled.set(true) }
            )
        }
        composeTestRule
            .onNode(withRole(Role.Button).and(hasText(submitLabel)))
            .performClick()
        assertTrue(onSubmitCalled.get())
    }
}
