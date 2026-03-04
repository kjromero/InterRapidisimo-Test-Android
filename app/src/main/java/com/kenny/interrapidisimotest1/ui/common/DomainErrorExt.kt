package com.kenny.interrapidisimotest1.ui.common

import com.kenny.interrapidisimotest1.domain.model.DomainError

fun DomainError.toUiMessage(): String = when (this) {
    DomainError.Network -> "Sin conexión. Verifica tu red e intenta de nuevo."
    DomainError.Unauthorized -> "Credenciales incorrectas."
    DomainError.Server -> "Error en el servidor. Intenta más tarde."
    DomainError.Unknown -> "Ocurrió un error inesperado."
}