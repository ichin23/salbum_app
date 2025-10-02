package com.ichin23.salbum.ui.screens.Signup

sealed class SignupEvents {
    data class OnNameChange(val name:String ): SignupEvents()
    data class OnUsernameChange(val username:String ): SignupEvents()
    data class OnEmailChange(val email:String ): SignupEvents()
    data class OnPasswordChange(val password:String ): SignupEvents()
    object OnShowPasswordClick: SignupEvents()
    object OnSignupClick: SignupEvents()

}