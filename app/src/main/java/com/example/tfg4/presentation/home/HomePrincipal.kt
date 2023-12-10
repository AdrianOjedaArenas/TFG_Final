package com.example.tfg4.presentation.home

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.service.autofill.OnClickAction
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.example.tfg4.Database.Controller
import com.example.tfg4.Database.Eventos
import com.example.tfg4.R
import com.example.tfg4.Utilities.DiferenciaEntreFechaActual
import com.example.tfg4.Utilities.SharedViewModelEvento
import com.example.tfg4.navigation.Destinations
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun principal(
     mainViewModel:MainViewModel,
     navController: NavHostController,
     sharedViewModelEvento: SharedViewModelEvento
){
    val searchWidgetState by mainViewModel.searchWidgetState
    //val searchTextState by mainViewModel.searchTextState

    var searchTextState by remember { mutableStateOf("") }

    Scaffold(
        //Barra de busqueda
        topBar = {
            MainAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                               newText -> run { searchTextState = newText }
                    //mainViewModel.updateSearchTextState(newValue = it)
                },
                onCloseClicked = {
                    mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onSearchClicked = {
                    Log.d("Searched Text", it)

                },
                onSearchTriggered = {
                    mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                }
            )
        },

       floatingActionButton = {bottomCreateEvent(modifier = Modifier, navController)},
        //Barra de botones de navegacion
        bottomBar = {

            BottomNavigation(modifier = Modifier,navController)
        }

    ) {
        if(searchTextState.isBlank() || searchTextState.isEmpty())
            EventList(navController,sharedViewModelEvento)
        else
            //Lista con los datos filtrados
            FilteredEventList(searchTextState,navController,sharedViewModelEvento)


    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageCard(evento :Eventos,navController: NavHostController,sharedViewModelEvento : SharedViewModelEvento) {

    val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
    val formattedDate : String? = evento.fecha?.let { dateFormat.format(it) }

    // Add padding around our message
    Row(
        modifier = Modifier
            .padding(all = 10.dp)
            .clickable {

                sharedViewModelEvento.setEvento(evento)
                navController.navigate(Destinations.EventDetailsScreen.route)

            }
    ) {
        Image(
           painter = rememberImagePainter(
               data = evento.imagen,
               imageLoader = ImageLoader.Builder(LocalContext.current)
                   .build()
           ),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                evento.titulo?.let {
                    Text(
                        text = it,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontWeight = FontWeight.Bold)

                    )
                }

                Spacer(modifier = Modifier.size(20.dp))

                formattedDate?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .widthIn(min = 8.dp),

                        textAlign = TextAlign.End,
                    )
                }

                Spacer(modifier = Modifier.size(10.dp))

                evento.hora?.let{

                    Text(
                        text = it,
                        modifier = Modifier.widthIn(min = 8.dp),
                        textAlign = TextAlign.End,
                    )

                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                evento.descripcion?.let {
                    val maxLength = 90 // Número máximo de caracteres a mostrar
                    val truncatedText = if (it.length > maxLength) it.take(maxLength) + "..." else it

                    Text(
                        text = truncatedText,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                }

                Spacer(modifier = Modifier.size(20.dp))


                cuentaAtras(evento)

            }
        }
    }
}
//Lista de eventos
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventList(navController: NavHostController,sharedViewModelEvento: SharedViewModelEvento) {
    val c = Controller()
    val eventosListState = remember { mutableStateOf(emptyList<Eventos>()) }

        c.getAllEventos { eventosList ->
            eventosListState.value = eventosList
        }


    LazyColumn {
        items(eventosListState.value) { evento ->
            MessageCard(evento, navController,sharedViewModelEvento)
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FilteredEventList(searchTextState:String,navController: NavHostController,sharedViewModelEvento: SharedViewModelEvento) {
    val c = Controller()
    val filteredEventosListState = remember { mutableStateOf(emptyList<Eventos>()) }

    c.getEvento(searchTextState) { eventosList ->
            filteredEventosListState.value = eventosList
    }

    LazyColumn {
        items(filteredEventosListState.value) { evento ->
            MessageCard(evento, navController,sharedViewModelEvento)
        }
    }
}

@Composable
private fun bottomCreateEvent(modifier: Modifier = Modifier,navController:NavHostController) {

    FloatingActionButton(
        modifier = modifier
            .size(50.dp)
            ,
        backgroundColor = colors.primary,

        onClick = {
            //navegacion pantalla formulario para crear evento
            navController.navigate(Destinations.CreateEvent.route)

        }
    ) {
        Icon(
            modifier = Modifier.size(42.dp),
            imageVector = Icons.Default.Build,//Hay que poner icono de "+"
            contentDescription = "Forward Icon",
            tint = Color.White
        )
    }
}


@Composable
private fun BottomNavigation(modifier: Modifier = Modifier, navController:NavHostController) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text("HOME")
            },
            selected = true,
            onClick = {
                navController.navigate(Destinations.Home.route)


            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                Text("PROFILE")
            },
            selected = false,
            onClick = {
                //Falta meter navegacion

                navController.navigate(Destinations.perfil.route)




            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun cuentaAtras(evento:Eventos){
    val cuentaAtras  = remember { mutableStateOf("") }
    val horaEvento = evento.hora
    val fechaEvento = evento.fecha
    val c = Controller()
    val deleteEventoState = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {

   var  tiempoRestante = horaEvento?.let { fechaEvento?.let { it1 -> DiferenciaEntreFechaActual(it1, it) } }

    val tiempo = tiempoRestante?.split(" ")

    val dias = tiempo?.get(0)?.toIntOrNull() ?: 0
    val horas = tiempo?.get(2)?.toIntOrNull() ?: 0
    val minutos = tiempo?.get(4)?.toIntOrNull() ?: 0
    val segundos = tiempo?.get(6)?.toIntOrNull() ?: 0

    val totalSegundos = segundos + minutos * 60 + horas * 3600 + dias * 86400

    var timer = Timer()
    val handler = Handler(Looper.getMainLooper())

    timer.schedule(object : TimerTask() {
        var contador = totalSegundos

        override fun run() {
            if (contador > 0) {
                val diasRestantes = contador / 86400
                val horasRestantes = (contador % 86400) / 3600
                val minutosRestantes = ((contador % 86400) % 3600) / 60
                val segundosRestantes = ((contador % 86400) % 3600) % 60

                val tiempoRestanteActualizado = "$diasRestantes d $horasRestantes h $minutosRestantes m $segundosRestantes s"

                handler.post {

                    // Aquí puedes actualizar la interfaz de usuario con el tiempo restante
                    cuentaAtras.value = tiempoRestanteActualizado

                }
                contador--

            } else {
                timer.cancel()

                deleteEventoState.value = true
            }
        }
    }, 0, 1000)
    }

    Text(
        text = cuentaAtras.value,
        modifier = Modifier.widthIn(min = 8.dp),
        textAlign = TextAlign.End

    )

    if (deleteEventoState.value) {
        evento.id?.let { c.deleteEvento(it) }
    }
}