package com.ichin23.salbum.data.api.dto.musicbrainz.release

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Recording(
    val id: String,
    val length: Int,
    val title:String,
    @SerializedName("first-release-date")
    val firstReleaseDate: String
)
