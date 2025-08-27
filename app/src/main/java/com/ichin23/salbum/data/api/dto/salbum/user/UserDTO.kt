package com.ichin23.salbum.data.api.dto.salbum.user

data class UserDTO(
    val id: String,
    val name: String,
    val username: String,
    val email: String,
    val image_url: String?,
    val followers_count: Int,
    val following_count: Int
)
