package com.ichin23.salbum.data.api.dto.salbum.user

import java.time.LocalDateTime

data class Token(
    val value: String,
    val expire_at: LocalDateTime
)
