package com.example.tfg4.Database

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import com.example.tfg4.Utilities.DiferenciaEntreFechaActual
import java.time.LocalDate
import java.time.LocalTime
import java.util.*


data class Eventos(

    var id:String? = null,
    var imagen: ImageBitmap? = null,
    val titulo: String? = null,
    val fecha: Date? = null,
    val hora: String? = null,
    val descripcion: String? = null

)