package com.example.tfg4.Database

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class Controller{

    private val db = Firebase.firestore

     @RequiresApi(Build.VERSION_CODES.O)
     @SuppressLint("SimpleDateFormat")
     fun crearEvento(titulo: String, fecha: String, hora: String, descripcion: String, callback: (String) -> Unit) {

         val dateFormat = SimpleDateFormat("dd/MM/yyyy")
         val fechaDate: Date = dateFormat.parse(fecha) as Date
         val formatterHora = DateTimeFormatter.ofPattern("HH:mm")
         val horaLocalTime :LocalTime = LocalTime.parse(hora,formatterHora)
         val horaLocalTimeStr :String = horaLocalTime.toString()

         val evento = Eventos(
             "",
             titulo,
             fechaDate,
             horaLocalTimeStr,
             descripcion
         )

         db.collection("eventos")
             .add(evento)
             .addOnSuccessListener { documentReference ->
                  val documentId = documentReference.id
                 // Aquí puedes utilizar el ID del documento como lo necesites
                 evento.id = documentId

                 //actualizamos el documento con la id que se acaba de crear automaticamente
                 documentReference.update("id",documentId)
                     .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
                     .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }

                 Log.d(TAG, "Documento creado con ID: $documentId")
                 callback(documentId)
             }
             .addOnFailureListener { e ->
                 Log.w(TAG, "Error al crear el documento", e)
                 callback(null.toString())
             }

     }

    fun getEvento(searchText :String,callback: (List<Eventos>) -> Unit){

        db.collection("eventos")
            .whereEqualTo("titulo", searchText)
            .get()
            .addOnSuccessListener { documents ->
                val filteredEvents = mutableListOf<Eventos>() // Lista para almacenar eventos filtrados
                for (document in documents) {
                    val event = document.toObject(Eventos::class.java)
                    filteredEvents.add(event) // Agregar evento filtrado a la lista
                }
                callback(filteredEvents)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error al obtener los eventos especificados: ", exception)

                callback(emptyList())
            }

    }

    fun getAllEventos(callback: (List<Eventos>) -> Unit) {
        db.collection("eventos").get()
            .addOnSuccessListener { querySnapshot ->
                val eventosList = mutableListOf<Eventos>()
                for (document in querySnapshot) {
                    val evento = document.toObject(Eventos::class.java)
                    eventosList.add(evento)
                }
                callback(eventosList)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error al obtener eventos: ", exception)
                callback(emptyList())
            }
    }

    fun deleteEvento(eventId: String){

        val docRef = db.collection("eventos").document(eventId)
        docRef.delete()
            .addOnSuccessListener {
                Log.d(TAG, "Evento eliminado correctamente")
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error al eliminar el evento: $exception")
            }

    }


     fun crearUser(email:String,password:String) {

        db.collection("usuarios").document(email).set(
                hashMapOf(
                    "Email" to email,
                    "Password" to password
                )
            )
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun getUser(emailP: String): MutableList<String> {

        val lista : MutableList<String> = mutableListOf()

        db.collection("usuarios").document(emailP).get().addOnSuccessListener {
             val email2 = it.get("Email") as String
             val password2 = it.get("Password") as String

            lista[0] = email2
            lista[1] = password2
        }

        return lista
    }

}