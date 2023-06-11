package com.example.tfg4.presentation.perfil

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tfg4.navigation.Destinations
import com.example.tfg4.presentation.components.RoundedButton

@Composable
fun perfilScreen(authViewModel: AuthViewModel,navController: NavHostController){
    val currentUser = authViewModel.currentUser.value

        if (currentUser != null) {
            Column(
                Modifier
                    .fillMaxSize()
            ) {
                Row() {
                    Text(
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        text = "Email: ${currentUser.email}")
                }

                Spacer(modifier = Modifier.size(20.dp))

                ButtonCloseSession(authViewModel, navController)
            }

        } else {
            Text(
                textAlign = TextAlign.Center,
                style = TextStyle(fontWeight = FontWeight.Bold),
                text = "No hay un usuario logueado"
            )
        }


}

@Composable
private fun ButtonCloseSession(authViewModel: AuthViewModel,navController: NavHostController){

    RoundedButton(
        text = "Cerrar sesion",
        displayProgressBar =  false,
        onClick = {
            authViewModel.signOut()
            navController.navigate(Destinations.Login.route)

        }
    )

}