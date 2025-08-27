package com.ichin23.salbum.core.utils

sealed class UiEvent {
    object PopBackStack: UiEvent()
    data class Navigate(val route: String): UiEvent()
    data class ShowSnackBar(
        val message: String,
        val action: String? =null,
    ):UiEvent()

}