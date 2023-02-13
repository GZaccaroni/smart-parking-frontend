package it.unibolss.smartparking.domain.usecases.user

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ValidateUserPasswordTest {

    lateinit var useCase: ValidateUserPassword

    @Before
    fun setUp() {
        useCase = ValidateUserPassword()
    }

    @Test
    fun testHappyCase() {
        assertTrue(useCase(ValidateUserPassword.Params("Giulio")))
        assertTrue(useCase(ValidateUserPassword.Params("Lorenzo")))
    }
    @Test
    fun testInvalidCase() {
        assertFalse(useCase(ValidateUserPassword.Params("aa")))
        assertFalse(useCase(ValidateUserPassword.Params("")))
        assertFalse(useCase(ValidateUserPassword.Params("Tst")))
        assertFalse(useCase(ValidateUserPassword.Params("--")))
    }
}