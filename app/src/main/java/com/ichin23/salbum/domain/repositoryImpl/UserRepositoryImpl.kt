package com.ichin23.salbum.domain.repositoryImpl

import androidx.datastore.core.DataStore
import com.ichin23.salbum.core.datastore.UserStateOuterClass
import com.ichin23.salbum.domain.models.User
import com.ichin23.salbum.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userStateDataStore: DataStore<UserStateOuterClass.UserState>
): UserRepository {
    override fun getCurrentUser(): User? {
        return runBlocking {
            val userState = userStateDataStore.data.first()
            if (userState.hasUser()) {
                User(
                    id = userState.user.id,
                    name = userState.user.name,
                    username = userState.user.username,
                    imageUrl = userState.user.imageUrl,
                    followersCount = 0,
                    followingCount = 0
                )
            } else {
                null
            }
        }
    }
}
