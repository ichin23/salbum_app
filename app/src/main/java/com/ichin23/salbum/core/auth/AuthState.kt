package com.ichin23.salbum.core.auth

sealed interface AuthState {
    object Loading : AuthState   // Estado inicial, enquanto lemos o DataStore
    object Authenticated : AuthState // Usuário está logado
    object Unauthenticated : AuthState // Usuário não está logado
}