package com.example.tfg4.Utilities

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun DiferenciaEntreFechaActual(fechaP: Date, hora:String):String {

    val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val fechaformateada = formato.format(fechaP)

    val fecha1: String = fechaformateada.toString() + " " + hora + ":00"

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    val fecha1LocalDateTime = LocalDateTime.parse(fecha1, formatter)

    val fechaActual = LocalDateTime.now()

    val diferencia = Duration.between(fechaActual, fecha1LocalDateTime)

    val segundosTotales = diferencia.seconds
    val dias = diferencia.toDays()
    val horas = (segundosTotales / 3600) % 24
    val minutos = (segundosTotales % 3600) / 60
    val segundos = segundosTotales % 60

    val tiempoRestante = "$dias d $horas h $minutos m $segundos s"

    return tiempoRestante

}
