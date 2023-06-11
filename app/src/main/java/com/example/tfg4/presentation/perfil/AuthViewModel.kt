package com.example.tfg4.presentation.perfil

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = mutableStateOf<FirebaseUser?>(null)

    init {
        firebaseAuth.addAuthStateListener { auth ->
            currentUser.value = auth.currentUser
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}