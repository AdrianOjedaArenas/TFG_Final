package com.example.tfg4.presentation.createEvent


import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.leonardom.loginjetpackcompose.navigation.Destinations
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.icons.filled.Image
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.tfg4.Database.Controller
import com.example.tfg4.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun createEventScreen(navController: NavHostController){

    val c = Controller()
    val context  = LocalContext.current
    val focusManager = LocalFocusManager.current
    // Variables para almacenar los valores del formulario
    var titulo by remember{ mutableStateOf("") }
    var fecha by remember{ mutableStateOf("") }
    var descripcion by remember{ mutableStateOf("") }

    //Calendario

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            if(mMonth>9 && mDayOfMonth>9)
                mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
            else if(mDayOfMonth<9 && mMonth>9)
                mDate.value = "0$mDayOfMonth/${mMonth + 1}/$mYear"
            else if(mDayOfMonth>9 && mMonth<9)
                mDate.value = "$mDayOfMonth/0${mMonth + 1}/$mYear"
            else
                mDate.value = "0$mDayOfMonth/0${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )


    //Fin calendario
Scaffold(
    topBar = {
        TopAppBar(
            title ={
                Text(
                "Crear evento",
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
            actions = {
                Row {
                    Button(
                        onClick = {
                            if ( comprobarDatos(titulo,fecha,context)) {

                                //Se añaden los datos a la base de datos
                                c.crearEvento(titulo,fecha,descripcion)
                                navController.navigate(Destinations.Home.route)

                            }
                        }
                    ) {
                        Text(text = "Guardar")
                        Spacer(Modifier.size(8.dp))
                    }
                }
            },
            backgroundColor = colorResource(R.color.light_grey_blue)

        )
    } ,
    content = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                AddImageButton(context, modifier = Modifier.align(Alignment.CenterHorizontally))

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = "Añadir imagen"
                )

                Spacer(modifier = Modifier.size(8.dp))

                Row() {
                    Spacer(modifier = Modifier.size(48.dp))

                    OutlinedTextField(
                        value = titulo,
                        onValueChange = { titulo = it },
                        label = { Text("Titulo") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    FloatingActionButton(
                        modifier = Modifier
                            .size(40.dp),
                        // .align(Alignment.CenterVertically),
                        backgroundColor = MaterialTheme.colors.primary,
                        onClick = {
                            //meter calendario
                            mDatePickerDialog.show()
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Calendar Icon",
                            tint = Color.White
                        )
                    }

                    fecha = mDate.value

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = fecha,
                        onValueChange = { fecha = it },
                        label = { Text("Fecha") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clickable {
                                mDatePickerDialog.show()
                            },
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row() {
                    Spacer(modifier = Modifier.size(48.dp))

                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripcion") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clickable {
                                mDatePickerDialog.show()
                            },
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

            }
    }
)

}



@RequiresApi(Build.VERSION_CODES.N)
fun comprobarFecha(fecha :String, context : Context) : Boolean {

    val regex = Regex("""\d{2}/\d{2}/\d{4}""")


    if (fecha.isBlank() or fecha.isEmpty()) {
        Toast.makeText(context, "Debes introducir una fecha", Toast.LENGTH_SHORT).show()
        return false
    } else if (!regex.matches(fecha))  {
        Toast.makeText(context, "El formato de fecha debe ser: DD/MM/YYYY", Toast.LENGTH_LONG)
            .show()
        return false
    } else if (regex.matches(fecha)) {

        if(validarFecha(fecha,"dd/mm/yyyy")) {

            return true
        }
        else{
            Toast.makeText(context, "Fecha inválida", Toast.LENGTH_LONG)
                .show()
            return false
        }

    }
    Toast.makeText(context, "Error", Toast.LENGTH_LONG)
        return false

}

fun validarFecha(fecha: String, formato: String): Boolean {
    val sdf = SimpleDateFormat(formato)
    sdf.isLenient = false // Configurar la comprobación estricta de la fecha

    return try {
        sdf.parse(fecha) // Intentar analizar la fecha
        true // La fecha es válida
    } catch (e: Exception) {
        false // La fecha es inválida
    }
}

@RequiresApi(Build.VERSION_CODES.N)
fun comprobarDatos(titulo:String, fecha:String, context:Context):Boolean{

    if ( titulo.isNotBlank() && titulo.isNotEmpty()  && comprobarFecha(fecha, context)) {

        Toast.makeText(context, "Evento creado", Toast.LENGTH_LONG)
            .show()

        return true
    }
    else if( titulo.isBlank() || titulo.isEmpty()  && comprobarFecha(fecha, context)) {

        Toast.makeText(context, " 'Titulo' no puede estar vacio", Toast.LENGTH_LONG)
            .show()

        return false
    }
    else if( titulo.isNotBlank() && titulo.isNotEmpty()  && !comprobarFecha(fecha, context))

        return false

    else {

        Toast.makeText(context, " ERROR", Toast.LENGTH_LONG)
            .show()

        return false
    }

}


@Composable
fun AddImageButton(context:Context, modifier:Modifier) {
    val colorPurple = colorResource(id = R.color.purple_200)

    val imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            if (uri != null) {
                val inputStream = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                imageBitmap.value = bitmap.asImageBitmap()
            }
        }
    }

    Column (
        modifier = modifier
            ){
        if (imageBitmap.value != null) {
            Image(
                bitmap = imageBitmap.value!!,
                contentDescription = "Selected Image",
                modifier = Modifier.size(200.dp)
            )
        } else {

            FloatingActionButton(
                backgroundColor = colorPurple,
                onClick = {
                    val intent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    pickImageLauncher.launch(intent)
                }
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Default.Image,
                    contentDescription = "Calendar Icon",
                    tint = Color.White
                )
            }
        }
    }
}








