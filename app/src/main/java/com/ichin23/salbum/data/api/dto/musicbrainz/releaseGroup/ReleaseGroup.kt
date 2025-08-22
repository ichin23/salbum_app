package com.ichin23.salbum.data.api.dto.musicbrainz.releaseGroup

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ReleaseGroup(
    val id: String,
    val score: Int,
    val count: Int,
    val title: String,
    var image:String?,
    @SerializedName("first-release-date")
    val firstReleaseDate: String,
    @SerializedName("primary-type")
    val primaryType: String,
    val releases: List<Release>
)
