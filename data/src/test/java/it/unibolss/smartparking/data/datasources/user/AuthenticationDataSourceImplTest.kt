package it.unibolss.smartparking.data.datasources.user

import it.unibolss.smartparking.data.models.user.AuthenticationInfo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class AuthenticationDataSourceImplTest {

    private lateinit var authenticationDataSource: AuthenticationDataSource

    @Before
    fun setUp() {
        authenticationDataSource = AuthenticationDataSourceImpl(
            RuntimeEnvironment.getApplication().applicationContext
        )
    }

    @Test
    fun getCurrentAuthInfoInitial() {
        assertEquals(
            null,
            authenticationDataSource.getCurrentAuthInfo(),
        )
    }

    @Test
    fun setCurrentAuthInfo() {
        val newUserId = "abc"
        val newAuthToken = "123"
        assertEquals(
            null,
            authenticationDataSource.getCurrentAuthInfo(),
        )
        authenticationDataSource.setCurrentAuthInfo(
            AuthenticationInfo(
                newUserId,
                newAuthToken
            )
        )

        assertEquals(
            AuthenticationInfo(newUserId, newAuthToken),
            authenticationDataSource.getCurrentAuthInfo(),
        )

        authenticationDataSource.setCurrentAuthInfo(null)

        assertEquals(
            null,
            authenticationDataSource.getCurrentAuthInfo(),
        )
    }
}