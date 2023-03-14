package it.unibolss.smartparking.data.datasources.user

import it.unibolss.smartparking.data.models.user.AuthenticationResult
import it.unibolss.smartparking.data.models.user.ChangePasswordRequestBody
import it.unibolss.smartparking.data.models.user.LoginRequestBody
import it.unibolss.smartparking.data.models.user.SignUpRequestBody
import it.unibolss.smartparking.data.models.user.UserDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

internal interface UserDataSource {

    @POST("user/sign-up")
    suspend fun signUp(
        @Body
        body: SignUpRequestBody
    ): AuthenticationResult

    @POST("user/login")
    suspend fun login(
        @Body
        body: LoginRequestBody
    ): AuthenticationResult

    @POST("user/change-password")
    suspend fun changeUserPassword(
        @Body
        body: ChangePasswordRequestBody
    )

    @GET("user/current")
    suspend fun getUser(): UserDto

    @DELETE("user/current")
    suspend fun deleteUser()
}
