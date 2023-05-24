package com.example.tfg4.presentation.createEvent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.tfg4.R
import com.example.tfg4.presentation.components.RoundedButton
import dev.leonardom.loginjetpackcompose.navigation.Destinations

@Composable
fun createEventScreen(navController: NavHostController){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp)
    )
    {
        Row(
            modifier = Modifier
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            RoundedButton(
                modifier = Modifier
                    .size(50.dp)
                    .wrapContentWidth(Alignment.Start),
                text = "x",
                false,
                onClick = {
                    //boton de cancelar volvemos a la pantalla principal
                    navController.navigate(Destinations.Home.route)
                }
            )

            Spacer(modifier = Modifier.size(15.dp))

            Text(
                text = "Crear evento",
                style = MaterialTheme.typography.h6.copy(
                    color = Color.Blue
                ),
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.size(15.dp))

            RoundedButton(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center),
                text = "Guardar",
                false,
                onClick = {
                    //boton de Guardar guardamos el evento en la base de datos y  volvemos a la pantalla principal


                    navController.navigate(Destinations.Home.route)
                }
            )


        }
    }
}
