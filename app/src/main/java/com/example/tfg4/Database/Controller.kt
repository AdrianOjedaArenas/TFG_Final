package com.example.tfg4.Database

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Controller{
    val db = Firebase.firestore

     fun crearEvento() {
        val evento = Eventos(
            1,
            "Evento1"
        )

        db.collection("eventos")
            .add(evento)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
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