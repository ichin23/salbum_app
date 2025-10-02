package com.ichin23.salbum.core.auth

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.protobuf.Timestamp
import com.ichin23.salbum.core.utils.toProtoTimestamp
import com.ichin23.salbum.data.api.dto.salbum.user.LoginDTO
import com.ichin23.salbum.data.api.dto.salbum.user.UserDTO
import com.ichin23.salbum.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.ZoneOffset
import javax.inject.Inject

import com.ichin23.salbum.core.datastore.UserStateOuterClass.UserState
import com.ichin23.salbum.data.api.ApiSalbumService
import com.ichin23.salbum.data.api.dto.salbum.user.UserLoginDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userStateDataStore: DataStore<UserState>,
    private val apiSalbumService: ApiSalbumService
): ViewModel() {
    val userState: StateFlow<UserState> = userStateDataStore.data.catch {
       exception ->
       if (exception is IOException){
           emit(UserState.getDefaultInstance())
       }else{
           throw exception
       }
   }.stateIn(
       scope = viewModelScope,
       started = SharingStarted.Lazily,
       initialValue = UserState.getDefaultInstance()
   )

    init {
        viewModelScope.launch {
            checkInitialAuthState()
        }
    }

    val _isLoggedIn = MutableStateFlow<AuthState>(AuthState.Loading)
    val isLoggedIn = _isLoggedIn.asStateFlow()


//    val isLoggedIn: StateFlow<AuthState> = flow<AuthState> {
//        emit(AuthState.Loading)
//
//        val result = checkInitialAuthState()
//
//        emit(result)
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.Eagerly,
//        initialValue = AuthState.Loading
//    )

    suspend fun checkInitialAuthState() {
        // .first() é a chave. Ele espera o primeiro valor REAL do DataStore ser emitido.
        val userState = userStateDataStore.data.first()

        if (userState.accessToken.value.isNotEmpty()) {
            _isLoggedIn.value = AuthState.Authenticated
        } else {
            _isLoggedIn.value = AuthState.Unauthenticated
        }
    }

}