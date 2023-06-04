package com.example.tfg4


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import com.example.tfg4.navigation.addCreateEvent
import com.example.tfg4.navigation.addHome
import com.example.tfg4.navigation.addLogin
import com.example.tfg4.navigation.addRegister
import com.example.tfg4.presentation.home.MainViewModel
import com.example.tfg4.ui.theme.TFG4Theme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import dev.leonardom.loginjetpackcompose.navigation.Destinations

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

     val mainViewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TFG4Theme {
                val navController = rememberAnimatedNavController()

                BoxWithConstraints {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Destinations.Login.route
                    ){
                        addLogin(navController)

                        addRegister(navController)

                        addHome(mainViewModel,navController)

                        addCreateEvent(navController)
                    }
                }
            }
        }
    }
}

