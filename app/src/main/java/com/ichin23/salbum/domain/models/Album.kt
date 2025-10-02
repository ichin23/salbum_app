package com.ichin23.salbum.domain.models

import com.google.gson.annotations.SerializedName

data class Album(
    val id: String,
    val name: String,
    val country:String,
    //val albumType: String,
    val releaseDate: String,
    val artists: List<Artist>,
    val imagesUrl: String,
    val externalUrls: ExternalUrls
)