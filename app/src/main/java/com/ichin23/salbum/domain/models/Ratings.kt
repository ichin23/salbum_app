package com.ichin23.salbum.domain.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Ratings(
    val rate: Double,
    @SerializedName("updated_at")
    val updatedAt: LocalDateTime,
    @SerializedName("created_at")
    val createdAt: LocalDateTime,
    val user: User,
    val album: Album
)
