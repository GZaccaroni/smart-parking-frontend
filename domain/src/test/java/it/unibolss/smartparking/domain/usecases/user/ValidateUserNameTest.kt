package it.unibolss.smartparking.domain.usecases.user

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ValidateUserNameTest {

    lateinit var useCase: ValidateUserName

    @Before
    fun setUp() {
        useCase = ValidateUserName()
    }

    @Test
    fun testHappyCase() {
        assertTrue(useCase(ValidateUserName.Params("Giulio")))
        assertTrue(useCase(ValidateUserName.Params("Lorenzo")))
    }
    @Test
    fun testInvalidCase() {
        assertFalse(useCase(ValidateUserName.Params("        ")))
        assertFalse(useCase(ValidateUserName.Params("        Giu")))
        assertFalse(useCase(ValidateUserName.Params("Tst")))
        assertFalse(useCase(ValidateUserName.Params("N N          ")))
        assertFalse(useCase(ValidateUserName.Params("Giuliongelngeo")))
    }
}