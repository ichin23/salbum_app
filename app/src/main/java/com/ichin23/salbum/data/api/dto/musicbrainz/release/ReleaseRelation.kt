package com.ichin23.salbum.data.api.dto.musicbrainz.release

import com.google.gson.annotations.SerializedName

data class ReleaseRelation(
    val type: String,
    val url: UrlResource,
    @SerializedName("target-type")
    val targetType: String
)
