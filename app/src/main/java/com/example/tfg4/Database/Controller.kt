package com.example.tfg4.Database

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class Controller{
    val db = Firebase.firestore

     @SuppressLint("SimpleDateFormat")
     fun crearEvento(titulo: String, fecha: String, descripcion: String) {

         GlobalScope.launch {
             try {

                 val format = SimpleDateFormat("yyyy-MM-dd")
                 val fechaDate: Date = format.parse(fecha)

                 val evento = Eventos(
                     titulo,
                     fechaDate,
                     descripcion
                 )

                 db.collection("eventos")
                     .add(evento)
                    // .await() // Esperar a que la operación se complete

                 Log.d(TAG, "DocumentSnapshot successfully written!")
             } catch (e: Exception) {
                 Log.w(TAG, "Error writing document", e)
             }
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