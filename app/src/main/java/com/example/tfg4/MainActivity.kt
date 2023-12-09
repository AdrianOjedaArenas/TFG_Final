package com.example.tfg4


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tfg4.Utilities.SharedViewModelEvento
import com.example.tfg4.navigation.*
import com.example.tfg4.presentation.home.MainViewModel
import com.example.tfg4.presentation.perfil.AuthViewModel
import com.example.tfg4.ui.theme.TFG4Theme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val sharedViewModel: SharedViewModelEvento by viewModels()
    private lateinit var authViewModel: AuthViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
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

                        addHome(mainViewModel,navController,sharedViewModel)

                        addCreateEvent(navController)

                        addPerfil(authViewModel,navController,sharedViewModel)

                        addDetails(navController,sharedViewModel)
                    }
                }
            }
        }
    }
}

