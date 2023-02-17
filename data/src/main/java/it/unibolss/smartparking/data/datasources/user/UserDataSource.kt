package it.unibolss.smartparking.data.datasources.user

import it.unibolss.smartparking.data.models.user.ChangePasswordRequestBody
import it.unibolss.smartparking.data.models.user.LoginRequestBody
import it.unibolss.smartparking.data.models.user.SignUpRequestBody
import it.unibolss.smartparking.data.models.user.UserDto
import retrofit2.Response

internal interface UserDataSource {

    suspend fun signUp(
        user: SignUpRequestBody
    ): Response<Unit>

}
