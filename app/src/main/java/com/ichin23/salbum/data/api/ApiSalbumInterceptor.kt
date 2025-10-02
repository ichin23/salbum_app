package com.ichin23.salbum.data.api

import android.util.Log
import androidx.datastore.core.DataStore
import com.ichin23.salbum.core.datastore.UserStateOuterClass
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiSalbumInterceptor(
    private val userStateDataStore: DataStore<UserStateOuterClass.UserState>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        Log.i("Carregando User", "Carregando...")
        val userState = runBlocking { userStateDataStore.data.first() }
        Log.i("User Carregado", "Carregado")

        val token = userState.accessToken.value

        Log.i("Requisição:", originalRequest.url.toString())


        return if (token.isNotEmpty() && !originalRequest.url.toString().contains("/refreshToken")){
            val newRequest = originalRequest.newBuilder()
                .header("User-Agent", "Salbum/1.0")
                .addHeader("Authorization", "Bearer $token")
                .build()
            Log.i("Headers:", originalRequest.headers.toString())
            chain.proceed(newRequest)
        }else{
            chain.proceed(originalRequest)
        }
    }

}