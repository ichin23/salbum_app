package com.ichin23.salbum.ui.screens.Login

sealed class LoginEvents {
    data class OnEmailChange(val email:String ): LoginEvents()
    data class OnPasswordChange(val password:String ): LoginEvents()
    object OnShowPasswordClick: LoginEvents()
    object OnLoginClick: LoginEvents()

}