package com.ichin23.salbum.data.api.dto.salbum

import com.google.gson.annotations.SerializedName
import com.ichin23.salbum.data.api.dto.musicbrainz.release.ReleaseDTO
import com.ichin23.salbum.data.api.dto.salbum.rating.RatingDTO

data class ReleaseDetailsDTO(
    @SerializedName("release") val release: ReleaseDTO,
    val ratings: List<RatingDTO>,
    val userRating: RatingDTO?
)
