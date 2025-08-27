package com.ichin23.salbum.data.api.dto.musicbrainz.release

import com.google.gson.annotations.SerializedName
import com.ichin23.salbum.data.api.dto.musicbrainz.LinksHateoas

data class ReleaseDTO(
    val date: String,
    val barcode: String,
    val country: String,
    val status:String,
    val title: String,
    val id: String,
    var image:String,
    val relations: List<ReleaseRelation>,
    val media: List<ReleaseMedia>,
    @SerializedName("artist-credit")
    val artistCredit: List<ArtistCreditLookup>,
    @SerializedName("_links") val links: LinksRelease
)