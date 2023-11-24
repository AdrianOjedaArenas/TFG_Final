package com.example.tfg4.presentation.EventDetails

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tfg4.Database.Eventos
import com.example.tfg4.presentation.home.MainViewModel

@Composable
fun DetailsScreen(
    navController: NavHostController
) {


   Column() {

       //Titulo
        evento.titulo?.let {
            Text(
                text = it,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = TextStyle(fontWeight = FontWeight.Bold)

            )
        }

       Spacer(modifier = Modifier.size(20.dp))

       evento.descripcion

       Spacer(modifier = Modifier.size(20.dp))




    }//Termina columna


}

fun detaisl(eventos: Eventos){



}


