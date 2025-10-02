package com.ichin23.salbum.data.api.dto.salbum.rating

import com.google.gson.annotations.SerializedName
import com.ichin23.salbum.data.api.dto.musicbrainz.release.LinksRelease
import com.ichin23.salbum.data.api.dto.salbum.user.UserDTO
import java.time.LocalDateTime

data class RatingDTO(
    val rate: Double,
    val created_at: LocalDateTime,
    val updated_at: LocalDateTime,
    val user: UserDTO,
    val album: String,
    @SerializedName("_links") val links: LinksRelease
)
