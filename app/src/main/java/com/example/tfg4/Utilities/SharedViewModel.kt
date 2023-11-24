package com.example.tfg4.Utilities

import androidx.lifecycle.ViewModel
import com.example.tfg4.Database.Eventos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class SharedViewModelEvento : ViewModel() {
    private val _evento = MutableStateFlow<Eventos?>(null)
    val evento: StateFlow<Eventos?> = _evento

    fun setEvento(evento: Eventos) {
        _evento.value = evento
    }
}
