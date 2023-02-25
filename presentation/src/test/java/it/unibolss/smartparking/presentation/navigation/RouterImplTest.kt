package it.unibolss.smartparking.presentation.navigation

import androidx.navigation.NavOptions
import app.cash.turbine.testIn
import io.mockk.MockKAnnotations
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RouterImplTest {

    private lateinit var router: RouterImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        router = RouterImpl()
    }

    @Test
    fun testNavigateTo() = runTest {
        val route = mockk<Route>()
        val navOptions = mockk<NavOptions>()

        val commandsTurbine = router.commands.testIn(backgroundScope)

        router.navigateTo(route, navOptions)
        assertEquals(commandsTurbine.awaitItem(), RouterCommand.NavigateTo(route, navOptions))
    }

    @Test
    fun testPopBackStack() = runTest {
        val commandsTurbine = router.commands.testIn(backgroundScope)

        router.popBackStack()
        assertEquals(commandsTurbine.awaitItem(), RouterCommand.PopBackStack)
    }
}
