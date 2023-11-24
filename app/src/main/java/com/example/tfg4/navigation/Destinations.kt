package com.example.tfg4.navigation

import androidx.navigation.compose.NamedNavArgument

sealed class Destinations(
    val route: String,
    val arguments: List<NamedNavArgument>
){

    object Login: Destinations("login", emptyList())
    object Register: Destinations("register", emptyList())
    object Home: Destinations("home", emptyList())
    object CreateEvent :Destinations("createEvent", emptyList())
    object perfil :Destinations("perfil", emptyList())
    object EventDetailsScreen :Destinations("EventDetailsScreen", emptyList())

}
