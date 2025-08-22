package com.ichin23.salbum.data.api.dto.musicbrainz.release

import com.google.gson.annotations.SerializedName

data class ArtistLookup(
    val id: String,
    val name: String,
    val type: String,
    @SerializedName("sort-name")
    val sortName:String,
    val country: String
)
