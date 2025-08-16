package com.ichin23.salbum.domain.repository

import com.ichin23.salbum.domain.models.User

interface UserRepository {
    fun getCurrentUser(): User?
}