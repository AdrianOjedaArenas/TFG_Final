package com.example.tfg4.presentation.EventDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.example.tfg4.Database.Controller
import com.example.tfg4.Database.Eventos
import com.example.tfg4.Utilities.SharedViewModelEvento
import com.example.tfg4.navigation.Destinations
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun DetailsScreen(
     navController: NavHostController,sharedViewModel: SharedViewModelEvento
) {
    Scaffold(
        topBar = {

            TopAppBar(
                title ={
                    Text(
                        "Detalles",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 25.sp
                    )
                },
                navigationIcon = {

                    IconButton(onClick = {navController.navigate(Destinations.Home.route) })
                    {
                        Icon(Icons.Filled.Close, contentDescription = "Volver")
                    }

                },
            )
        },
        content = {

            LazyColumn {
                items(1) { index ->
                    messageCard(sharedViewModel = sharedViewModel)
                }
            }
            
            
        }
    )
}

@Composable
fun messageCard(sharedViewModel: SharedViewModelEvento){

    val c  = Controller()
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val evento = sharedViewModel.evento.value


    var inscrito by remember { mutableStateOf(false) }

    if (evento != null) {
        c.comprobarInscrito(user!!.uid,evento.id) { estaInscrito ->
            inscrito = estaInscrito
        }
    }


    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Mostrar la foto en grande
        if(evento?.imagen != "") {
            Image(
                painter = rememberImagePainter(
                    data = evento?.imagen,
                    imageLoader = ImageLoader.Builder(LocalContext.current)
                        .build()
                ),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }

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

            val formattedDate =
                it.fecha?.let { it1 -> dateFormat.format(it1) }// formateo fecha dd/mm/yyyy

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
                    onClick = {
                        evento.id?.let { it1 -> c.crearEventoUsuario(user!!.uid, it1) }
                        inscrito = !inscrito
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    enabled = !inscrito

                ) {
                    Text(text = "Unirse")
                }

                Button(
                    onClick = {
                        evento.id?.let { it1 -> c.deleteEventoUsuario(user!!.uid, it1) }
                        inscrito = !inscrito

                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    enabled = inscrito

                ) {
                    Text(text = "Quitarse")
                }
            }
        }
    }
}



