package it.unibolss.smartparking.domain.usecases.user

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ValidateUserEmailTest {

    lateinit var useCase: ValidateUserEmail

    @Before
    fun setUp() {
        useCase = ValidateUserEmail()
    }

    @Test
    fun testHappyCase() {
        assertTrue(useCase(ValidateUserEmail.Params("a@google.it")))
        assertTrue(useCase(ValidateUserEmail.Params("a+a@a.it")))
        assertTrue(useCase(ValidateUserEmail.Params("a.a+a@b.it")))
    }
    @Test
    fun testInvalidCase() {
        assertFalse(useCase(ValidateUserEmail.Params("google.it")))
        assertFalse(useCase(ValidateUserEmail.Params("a a@b.it")))
        assertFalse(useCase(ValidateUserEmail.Params("a a@b")))
    }
}