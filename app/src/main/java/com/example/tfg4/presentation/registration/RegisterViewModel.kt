package com.example.tfg4.presentation.registration

import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg4.Database.Controller
import com.example.tfg4.R
import com.google.firebase.auth.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {

    val state: MutableState<RegisterState> = mutableStateOf(RegisterState())

    val c = Controller()

    fun register(
        email: String,
        password: String,
        confirmPassword: String,
        context: Context,
        callback:(Boolean) -> Unit
    ) {
        var errorMessage: Int?

        if(email.isBlank() ||  password.isBlank() || confirmPassword.isBlank()){
            errorMessage = R.string.error_input_empty
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            errorMessage =  R.string.error_not_a_valid_email
        } else if(password != confirmPassword) {
            errorMessage = R.string.error_incorrectly_repeated_password
        } else {

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    c.crearUser(email,password)
                    callback(true)
                }
                .addOnFailureListener { e ->
                    when (e) {

                        is FirebaseAuthWeakPasswordException -> {
                            Toast.makeText(context, "Longitud minima: 6", Toast.LENGTH_SHORT)
                                .show()
                            callback(false)
                        }
                        is FirebaseAuthUserCollisionException -> {
                            Toast.makeText(context, "El usuario ya existe", Toast.LENGTH_SHORT)
                                .show()
                            callback(false)
                        }
                        is FirebaseAuthEmailException -> {
                            Toast.makeText(context, "Email no válido", Toast.LENGTH_SHORT)
                                .show()
                            callback(false)
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            Toast.makeText(
                                context,
                                "Credenciales inválidas",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            callback(false)
                        }

                        else -> {

                            Toast.makeText(
                                context,
                                " Error no gestionado de Firebase",
                                Toast.LENGTH_SHORT
                            ).show()
                            callback(false)
                        }
                    }
                }

                errorMessage = null
        }

        errorMessage?.let {
            state.value = state.value.copy(errorMessage = errorMessage)
            return
        }


        viewModelScope.launch {
            state.value = state.value.copy(displayProgressBar = true)

            delay(3000)

            state.value = state.value.copy(displayProgressBar = false)
        }
    }

    fun hideErrorDialog() {
        state.value = state.value.copy(
            errorMessage = null
        )
    }

}