package it.unibolss.smartparking.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import it.unibolss.smartparking.data.datasources.parkingslot.ParkingSlotDataSource
import it.unibolss.smartparking.data.datasources.user.UserDataSource
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

@OptIn(ExperimentalSerializationApi::class)
internal val dataSourceModule = module {
    val mediaType = MediaType.parse("application/json")!!
    val retrofit = Retrofit.Builder()
        .baseUrl("https://localhost/")
        .addConverterFactory(Json.asConverterFactory(mediaType))
        .build()

    single<ParkingSlotDataSource> {
        retrofit.create()
    }
    single<UserDataSource> {
        retrofit.create()
    }
}

internal val repositoryModule = module {
}

val dataModule get() = dataSourceModule + repositoryModule
