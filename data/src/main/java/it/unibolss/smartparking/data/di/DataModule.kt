package it.unibolss.smartparking.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import it.unibolss.smartparking.data.BuildConfig
import it.unibolss.smartparking.data.common.AppJson
import it.unibolss.smartparking.data.datasources.parkingslot.ParkingSlotDataSource
import it.unibolss.smartparking.data.datasources.user.AuthenticationDataSource
import it.unibolss.smartparking.data.datasources.user.AuthenticationDataSourceImpl
import it.unibolss.smartparking.data.datasources.user.UserDataSource
import it.unibolss.smartparking.data.repositories.parkingslot.ParkingSlotRepositoryImpl
import it.unibolss.smartparking.data.repositories.user.UserRepositoryImpl
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.MediaType
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.get
import retrofit2.Retrofit
import retrofit2.create

@OptIn(ExperimentalSerializationApi::class)
internal val dataSourceModule = module {
    val mediaType = MediaType.get("application/json")
    val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val authenticationDataSource = get<AuthenticationDataSource>(AuthenticationDataSource::class.java)
            val authInfo = authenticationDataSource.getCurrentAuthInfo()
            if (authInfo != null) {
                it.proceed(
                    it.request().newBuilder()
                        .addHeader("Authorization", "Bearer ${authInfo.authToken}")
                        .build()
                )
            } else {
                it.proceed(it.request())
            }
        }
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_ENDPOINT)
        .client(client)
        .addConverterFactory(AppJson.instance.asConverterFactory(mediaType))
        .build()

    single<ParkingSlotDataSource> {
        retrofit.create()
    }
    single<AuthenticationDataSource> {
        AuthenticationDataSourceImpl(get())
    }
    single<UserDataSource> {
        retrofit.create()
    }
}

internal val repositoryModule = module {
    single<UserRepository> {
        UserRepositoryImpl(get(), get())
    }
    single<ParkingSlotRepository> {
        ParkingSlotRepositoryImpl(get(), get())
    }
}

/**
 * Koin [Module] containing the dependencies injected by the data layer
 */
val dataModule get() = dataSourceModule + repositoryModule
