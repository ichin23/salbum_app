package com.ichin23.salbum.domain.repositoryImpl

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.ichin23.salbum.data.userJSON
import com.ichin23.salbum.domain.models.User
import com.ichin23.salbum.domain.repository.UserRepository
import com.ichin23.salbum.core.utils.LocalDateTimeDeserializer
import java.time.LocalDateTime

class UserRepostioryInMemory: UserRepository {
    var _currentUser: User? by mutableStateOf(null)

    init{
        val gson = Gson()

        val typeTokenUser = object : TypeToken<User>() {}.type

        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())

        val customGson = gsonBuilder.create()
        _currentUser = customGson.fromJson(userJSON, typeTokenUser)
    }

    override fun getCurrentUser(): User? {
        return _currentUser;
    }

}