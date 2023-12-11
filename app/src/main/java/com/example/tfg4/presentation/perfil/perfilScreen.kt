package com.example.tfg4.presentation.perfil

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tfg4.Database.Controller
import com.example.tfg4.Database.Eventos
import com.example.tfg4.Utilities.SharedViewModelEvento
import com.example.tfg4.navigation.Destinations
import com.example.tfg4.presentation.components.RoundedButton
import com.example.tfg4.presentation.home.*

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SuspiciousIndentation")
@Composable
fun PerfilScreen(mainViewModel:MainViewModel,authViewModel: AuthViewModel,navController: NavHostController,sharedViewModelEvento: SharedViewModelEvento){
    val currentUser = authViewModel.currentUser.value
    val idUsuario = currentUser?.uid

    val searchWidgetState by mainViewModel.searchWidgetState

    var searchTextState by remember { mutableStateOf("") }

    Scaffold(
        //Barra de busqueda
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Perfil",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {

                    IconButton(onClick = {navController.navigate(Destinations.Home.route) })
                    {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
            )
        },

        //Barra de botones de navegacion
        bottomBar = {

            BottomNavigation(modifier = Modifier,navController)
        }

    ) {

        if (currentUser != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        text = "Email: ${currentUser.email}"
                    )
                }

                Spacer(modifier = Modifier.size(20.dp))

                EventList(
                    idUsuario = idUsuario,
                    navController,
                    sharedViewModelEvento = sharedViewModelEvento
                )

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

}

//Lista de eventos
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventList(idUsuario : String?, navController: NavHostController,sharedViewModelEvento: SharedViewModelEvento) {
    val c = Controller()
    val eventosListState = remember { mutableStateOf(emptyList<Eventos>()) }

    c.historial(idUsuario) { eventosList ->
        eventosListState.value = eventosList
    }

    LazyColumn {
        items(eventosListState.value) { evento ->
            MessageCard(evento, navController,sharedViewModelEvento)
        }
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