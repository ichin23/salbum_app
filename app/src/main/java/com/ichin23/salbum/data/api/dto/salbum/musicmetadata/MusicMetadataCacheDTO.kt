package com.ichin23.salbum.data.api.dto.salbum.musicmetadata

import com.google.gson.annotations.SerializedName
import com.ichin23.salbum.data.api.dto.salbum.utils.LinksHateoas

data class MusicMetadataCacheDTO(
    var id: String,
    var type: String,
    var title: String,
    var artist_name:String,
    var has_image: Boolean,
    var length: Int,
    var references_album:String,

    @SerializedName("links") val links: List<LinksHateoas>
)
