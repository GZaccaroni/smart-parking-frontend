package it.unibolss.smartparking.data.datasources.user

import it.unibolss.smartparking.data.models.user.AuthenticationInfo

internal interface AuthenticationDataSource {
    fun getCurrentAuthInfo(): AuthenticationInfo?

    fun setCurrentAuthInfo(info: AuthenticationInfo?)
}
