package com.example.tfg4.presentation.EventDetails

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tfg4.Utilities.SharedViewModelEvento
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun DetailsScreen(
    navController: NavHostController,sharedViewModel: SharedViewModelEvento
) {

    val evento = sharedViewModel.evento.value

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Mostrar la foto en grande

        Spacer(modifier = Modifier.size(16.dp))

        // Título, fecha y hora
        evento?.let {
            Text(
                text = it.titulo ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            )

            val formattedDate = it.fecha?.let { it1 -> dateFormat.format(it1) }// formateo fecha dd/mm/yyyy

            Text(
                text = "Fecha: ${formattedDate}   Hora: ${it.hora}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )

            Spacer(modifier = Modifier.size(16.dp))

            // Descripción
            Text(
                text = it.descripcion ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 18.sp)
            )

            Spacer(modifier = Modifier.size(16.dp))

            // Botones para unirse o quitarse del evento
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /* Lógica para unirse al evento */ },
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                ) {
                    Text(text = "Unirse")
                }

                Button(
                    onClick = { /* Lógica para quitarse del evento */ },
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                ) {
                    Text(text = "Quitarse")
                }
            }
        }
    }
}


