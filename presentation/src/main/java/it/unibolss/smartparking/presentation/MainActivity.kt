package it.unibolss.smartparking.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import it.unibolss.smartparking.domain.entities.user.AuthState
import it.unibolss.smartparking.domain.usecases.common.invoke
import it.unibolss.smartparking.domain.usecases.user.GetAuthState
import it.unibolss.smartparking.presentation.common.SmartParkingApp
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val getAuthState: GetAuthState by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartParkingApp(isLoggedIn = getAuthState() is AuthState.LoggedIn)
        }
    }
}