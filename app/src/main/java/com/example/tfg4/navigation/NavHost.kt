package com.example.tfg4.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.tfg4.presentation.createEvent.createEventScreen
import com.example.tfg4.presentation.home.MainViewModel
import com.example.tfg4.presentation.home.principal
import com.example.tfg4.presentation.login.LoginScreen
import com.example.tfg4.presentation.login.LoginViewModel
import com.example.tfg4.presentation.registration.RegisterViewModel
import com.example.tfg4.presentation.registration.RegistrationScreen
import com.google.accompanist.navigation.animation.composable
import dev.leonardom.loginjetpackcompose.navigation.Destinations


@ExperimentalAnimationApi
fun NavGraphBuilder.addLogin(
    navController: NavHostController
) {
    composable(
        route = Destinations.Login.route,
        enterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { 1000 },
                animationSpec = tween(500)
            )
        },
        exitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { -1000 },
                animationSpec = tween(500)
            )
        },
        popEnterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { -1000 },
                animationSpec = tween(500)
            )
        },
        popExitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { 1000 },
                animationSpec = tween(500)
            )
        }
    ) {
        val viewModel: LoginViewModel = hiltViewModel()
        val email = viewModel.state.value.email
        val password = viewModel.state.value.password

        if (viewModel.state.value.successLogin) {
            navController.navigate(
                Destinations.Home.route
            ) {
                popUpTo(Destinations.Login.route) {
                    inclusive = true
                }
            }
        } else {
            LoginScreen(
                state = viewModel.state.value,
                onLogin = viewModel::login,
                onNavigateToRegister = {
                    navController.navigate(Destinations.Register.route)
                },
                onDismissDialog = viewModel::hideErrorDialog,
                navController
            )
        }

    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addRegister(
    navController: NavHostController
) {
    composable(
        route = Destinations.Register.route,
        enterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { 1000 },
                animationSpec = tween(500)
            )
        },
        exitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { -1000 },
                animationSpec = tween(500)
            )
        },
        popEnterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { -1000 },
                animationSpec = tween(500)
            )
        },
        popExitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { 1000 },
                animationSpec = tween(500)
            )
        }
    ) {
        val viewModel: RegisterViewModel = hiltViewModel()

        RegistrationScreen(
            state = viewModel.state.value,
            onRegister = viewModel::register,
            onBack = {
                navController.popBackStack()
            },
            onDismissDialog = viewModel::hideErrorDialog
        )
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addHome(mainViewModel: MainViewModel, events: List<String>,navController: NavHostController) {
    composable(
        route = Destinations.Home.route
    ) {

        principal(mainViewModel, events,navController)

    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addCreateEvent(
    navController: NavHostController
) {
    composable(
        route = Destinations.CreateEvent.route
    ) {

        createEventScreen(navController)

    }
}
