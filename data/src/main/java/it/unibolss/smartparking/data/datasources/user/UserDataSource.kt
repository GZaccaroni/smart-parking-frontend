package it.unibolss.smartparking.data.datasources.user

import it.unibolss.smartparking.data.models.user.ChangePasswordRequestBody
import it.unibolss.smartparking.data.models.user.LoginRequestBody
import it.unibolss.smartparking.data.models.user.SignUpRequestBody
import it.unibolss.smartparking.data.models.user.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface UserDataSource {

    @POST("user/sign-up")
    suspend fun signUp(
        @Body
        body: SignUpRequestBody
    ): Response<UserDto>

    @POST("user/login")
    suspend fun login(
        @Body
        body: LoginRequestBody
    ): Response<UserDto>

    @POST("user/logout")
    suspend fun logout(): Response<Unit>

    @POST("user/{id}/change-password")
    suspend fun changeUserPassword(
        body: ChangePasswordRequestBody
    ): Response<Unit>

    @GET("user/{id}")
    suspend fun getUser(
        @Path("id")
        id: String,
    ): Response<UserDto>

    @DELETE("user/{id}")
    suspend fun deleteUser(
        @Path("id")
        id: String,
    ): Response<Unit>
}
