package com.ichin23.salbum.data.api.dto.salbum.rating

import com.google.gson.annotations.SerializedName
import com.ichin23.salbum.data.api.dto.musicbrainz.release.LinksRelease
import com.ichin23.salbum.data.api.dto.salbum.user.UserDTO
import com.ichin23.salbum.data.api.dto.salbum.utils.LinksHateoas
import java.time.LocalDateTime

data class RatingUserDTO(
    val rate: Double,
    val created_at: LocalDateTime,
    val updated_at: LocalDateTime,
    val user: UserDTO,
    val album: String,
    val links: List<LinksHateoas>
)
