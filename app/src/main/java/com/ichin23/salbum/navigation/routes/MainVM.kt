package com.ichin23.salbum.navigation.routes

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ichin23.salbum.core.auth.AuthViewModel
import com.ichin23.salbum.core.datastore.UserStateOuterClass
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.ichin23.salbum.core.datastore.UserStateOuterClass.UserState
import com.ichin23.salbum.core.utils.toProtoTimestamp
import com.ichin23.salbum.data.api.ApiSalbumService
import com.ichin23.salbum.data.api.dto.salbum.user.RefreshTokenOut
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.Instant


@HiltViewModel
class MainVM @Inject constructor(
    private val salbumService: ApiSalbumService,
    private val userStateDataStore: DataStore<UserState>
): ViewModel() {

    private var _loading = MutableStateFlow<Boolean>(true)
    var loading = _loading.asStateFlow()

    fun checkToken(authViewModel: AuthViewModel){
        viewModelScope.launch {
            Log.i("Refresh_Expire_At", Instant.ofEpochSecond(authViewModel.userState.value.refreshToken.expireAt.seconds).toString())
            Log.i("Access_Expire_At", Instant.ofEpochSecond(authViewModel.userState.value.accessToken.expireAt.seconds).toString())
            var now = Instant.now()
            Log.i("NOW", now.toString())
            if(Instant.ofEpochSecond(authViewModel.userState.value.accessToken.expireAt.seconds).isBefore(now)){
                if(Instant.ofEpochSecond(authViewModel.userState.value.refreshToken.expireAt.seconds).isBefore(now)){
                    userStateDataStore.updateData {current->
                        UserStateOuterClass.UserState.getDefaultInstance()
                    }
                    _loading.value =false
                    return@launch
                }
                try {
                    val response =
                        salbumService.refreshToken(RefreshTokenOut(authViewModel.userState.value.refreshToken.value));

                userStateDataStore.updateData { current: UserState ->
                    current.toBuilder()
                        .setAccessToken(
                            UserState.Token.newBuilder()
                                .setValue(response.access_token.value)
                                .setExpireAt(response.access_token.expire_at.toProtoTimestamp())
                        ).setRefreshToken(
                            UserState.Token.newBuilder()
                                .setValue(response.refresh_token.value)
                                .setExpireAt(response.refresh_token.expire_at.toProtoTimestamp())
                        )
                        .setUser(
                            UserState.User.newBuilder()
                                .setId(response.user.id)
                                .setName(response.user.name)
                                .setUsername(response.user.username)
                                .setEmail(response.user.email)
                        ).build()
                }}catch (exception: HttpException){
                    Log.i("erro: ", exception.code().toString())
                    userStateDataStore.updateData {current->
                        UserStateOuterClass.UserState.getDefaultInstance()
                    }
                    return@launch
                }finally {
                    _loading.value=false
                }
            }
            _loading.value=false
        }
    }
}