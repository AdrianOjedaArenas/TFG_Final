package com.example.tfg4.Database

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Flow

class Controller{

    private val db = Firebase.firestore

     @SuppressLint("SimpleDateFormat")
     fun crearEvento(titulo: String, fecha: String, descripcion: String) {

         val format = SimpleDateFormat("dd/mm/yyyy")
         val fechaDate: Date = format.parse(fecha)

         val evento = Eventos(
             titulo,
             fechaDate,
             descripcion
         )

         db.collection("eventos")
             .add(evento)
             .addOnSuccessListener { documentReference ->
                 val documentId = documentReference.id
                 // Aquí puedes utilizar el ID del documento como lo necesites
                 Log.d(TAG, "Documento creado con ID: $documentId")
             }
             .addOnFailureListener { e ->
                 Log.w(TAG, "Error al crear el documento", e)
             }

     }

    fun getEvento(searchText :String,callback: (List<Eventos>) -> Unit){

        db.collection("eventos")
            .whereEqualTo("title", searchText)
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
            }
            callback(emptyList())

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