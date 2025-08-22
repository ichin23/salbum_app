package com.ichin23.salbum.data.api.dto.musicbrainz

import com.ichin23.salbum.domain.models.Artist

data class SearchArtistResponse(
    val id: String,
    val type:String,
    val score: Int,
    val gender: String,
    val name: String,
    val country: String,
    //val life-span: String
    val tags: List<Artist>
    )