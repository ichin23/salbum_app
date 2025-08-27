package com.ichin23.salbum.data.api.dto.salbum.rating

import java.time.LocalDateTime

data class RatingDTO(
    val rate: Float,
    val created_at: LocalDateTime,
    val updated_at: LocalDateTime,
    val user: String,
    val album: String
)
