package it.unibolss.smartparking.data.datasources.user

import android.content.Context
import it.unibolss.smartparking.data.models.user.AuthenticationInfo

internal class AuthenticationDataSourceImpl(
    context: Context,
) : AuthenticationDataSource {

    private val sharedPreferences =
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    override fun getCurrentAuthInfo(): AuthenticationInfo? {
        val currentAuthToken =
            sharedPreferences.getString(currentAuthTokenPrefKey, null)
        val currentUserId =
            sharedPreferences.getString(currentUserIdPrefKey, null)
        return if (currentAuthToken != null && currentUserId != null) {
            AuthenticationInfo(
                authToken = currentAuthToken,
                userId = currentUserId,
            )
        } else {
            null
        }
    }

    override fun setCurrentAuthInfo(info: AuthenticationInfo?) {
        with(sharedPreferences.edit()) {
            putString(currentAuthTokenPrefKey, info?.authToken)
            putString(currentUserIdPrefKey, info?.userId)
        }.apply()
    }

    companion object {
        private const val prefsName = "it.unibolss.smartparking.main"
        private const val currentAuthTokenPrefKey = "current_auth_token"
        private const val currentUserIdPrefKey = "current_user_id"
    }
}
