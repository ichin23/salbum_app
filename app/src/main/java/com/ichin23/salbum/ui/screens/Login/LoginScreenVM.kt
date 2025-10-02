package com.ichin23.salbum.ui.screens.Login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ichin23.salbum.core.auth.AuthViewModel
import com.ichin23.salbum.core.datastore.UserStateOuterClass.UserState
import com.ichin23.salbum.core.utils.UiEvent
import com.ichin23.salbum.core.utils.toProtoTimestamp
import com.ichin23.salbum.data.api.ApiSalbumService
import com.ichin23.salbum.data.api.dto.salbum.user.UserLoginDTO
import com.ichin23.salbum.navigation.ScreenName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginScreenVM @Inject constructor(
    private val apiSalbumService: ApiSalbumService,
    private val userStateDataStore: DataStore<UserState>
): ViewModel() {
    var emailState by mutableStateOf("")
        private set
    var passwordState by mutableStateOf("")
        private set
    var showPassword by mutableStateOf(false)
        private set

    var _loading = MutableStateFlow<Boolean>(false)
    var loading = _loading.asStateFlow()

    private val _uiEvents = Channel<UiEvent>()
    val uiEvent = _uiEvents.receiveAsFlow()

    fun onEvent(event: LoginEvents){
        when(event){
            is LoginEvents.OnEmailChange -> emailState=event.email
            is LoginEvents.OnPasswordChange -> passwordState=event.password
            LoginEvents.OnLoginClick -> onSendLogin()
            LoginEvents.OnShowPasswordClick -> showPassword=!showPassword
            LoginEvents.OnSignupClick -> sendUiEvent(UiEvent.Navigate(ScreenName.SIGNUP_SCREEN))
        }
    }

    fun onSendLogin() {
        viewModelScope.launch {
            _loading.value = true
            try {
               val user = apiSalbumService.loginUser(
                    UserLoginDTO(
                        emailState, passwordState
                    )
                )
                Log.i("Resultado login: ",user.toString())
                userStateDataStore.updateData { current ->
                    current.toBuilder()
                        .setAccessToken(
                            UserState.Token.newBuilder()
                                .setValue(user.access_token.value)
                                .setExpireAt(user.access_token.expire_at.toProtoTimestamp())
                        )
                        .setRefreshToken(
                            UserState.Token.newBuilder()
                                .setValue(user.refresh_token.value)
                                .setExpireAt(user.refresh_token.expire_at.toProtoTimestamp())
                        )
                        .setUser(
                            UserState.User.newBuilder()
                                .setId(user.user.id)
                                .setName(user.user.name)
                                .setUsername(user.user.username)
                                .setEmail(user.user.email)
                        )
                        .build()
                }
                sendUiEvent(UiEvent.Navigate(ScreenName.MAIN_ROUTE))
            }catch (e: HttpException){
                Log.e("Req error", e.response()?.raw()?.request?.headers.toString())
                Log.e("Req error", e.response()?.raw()?.request?.url.toString())
                Log.e("Req error", e.code().toString())
                Log.e("Req error", e.response()?.body().toString())
            }
            _loading.value=false
        }
    }

    private fun sendUiEvent(event:UiEvent){
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }
}