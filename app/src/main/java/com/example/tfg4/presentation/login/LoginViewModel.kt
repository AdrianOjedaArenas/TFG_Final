package com.example.tfg4.presentation.login

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg4.Database.Controller
import com.example.tfg4.Database.Users
import com.example.tfg4.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.security.auth.callback.Callback

class LoginViewModel: ViewModel() {

    val state: MutableState<LoginState> = mutableStateOf(LoginState())

    fun login(email: String, password: String,context: Context,callback:(Boolean) -> Unit) {

        try {

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnFailureListener { e ->
                    Toast.makeText(context,"Error al iniciar sesion",Toast.LENGTH_SHORT).show()
                    when (e) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            Toast.makeText(context,"Credenciales incorrectas",Toast.LENGTH_SHORT).show()
                            callback(false)
                        } //"Datos incorrectos"
                        is FirebaseAuthInvalidUserException -> {
                            Toast.makeText(context,"Usuario incorrecto",Toast.LENGTH_SHORT).show()
                            callback(false)
                        } //"Usuario no existe"
                        else -> {
                            Toast.makeText(context, " Error no gestionado de Firebase", Toast.LENGTH_SHORT).show()
                            callback(false)
                        }
                    }
                }.addOnSuccessListener {

                    callback(true)
                }


        }catch (error: Exception) {
            Toast.makeText(context,"No dejes campos vacios", Toast.LENGTH_SHORT).show()
        }

        viewModelScope.launch {
            state.value = state.value.copy(displayProgressBar = true)

            delay(3000)

            state.value = state.value.copy(email = email, password = password)
            state.value = state.value.copy(displayProgressBar = false)
        }
    }

    fun hideErrorDialog() {
        state.value = state.value.copy(
            errorMessage = null
        )
    }

}