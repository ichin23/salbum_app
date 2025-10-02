package com.ichin23.salbum.ui.screens.Signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.ichin23.salbum.core.utils.UiEvent
import com.ichin23.salbum.data.api.ApiSalbumService
import com.ichin23.salbum.data.api.dto.salbum.user.UserRegisterDTO
import com.ichin23.salbum.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupVM @Inject constructor(val salbumService: ApiSalbumService): ViewModel(){
    var nameState by mutableStateOf("")
        private set;

    var usernameState by mutableStateOf("")
        private set

    var emailState by mutableStateOf("")
        private set;

    var passwordState by mutableStateOf("")
        private set;

    private var _showPassword = MutableStateFlow(false)
    var showPassword = _showPassword.asStateFlow()

    private var _error  = MutableStateFlow<String?>(null)
    var error = _error.asStateFlow()

    private var _loading = MutableStateFlow(false)
    var loading = _loading.asStateFlow()

    private val _uiEvents = Channel<UiEvent>()
    val uiEvent = _uiEvents.receiveAsFlow()

    fun onEvent(event: SignupEvents){
        when(event){
            is SignupEvents.OnNameChange -> nameState=event.name
            is SignupEvents.OnUsernameChange -> usernameState=event.username
            is SignupEvents.OnEmailChange -> emailState=event.email
            is SignupEvents.OnPasswordChange -> passwordState=event.password
            SignupEvents.OnShowPasswordClick -> {_showPassword.value=!_showPassword.value}
            SignupEvents.OnSignupClick -> onSignup()
        }
    }

    fun onSignup(){
        viewModelScope.launch {
            try {
                _loading.value = true
                var userRegister: UserRegisterDTO = UserRegisterDTO(
                    nameState,
                    usernameState,
                    emailState,
                    passwordState
                )
                var response = salbumService.registerUser(userRegister)
            }catch (e: HttpException){
                _error.value = "Ocorreu um erro ao se cadastrar"
            }finally {
                _loading.value=false
            }
        }
    }

    private fun sendUiEvent(event:UiEvent){
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }

}