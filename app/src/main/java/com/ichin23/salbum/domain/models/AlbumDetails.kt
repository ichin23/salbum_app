package com.ichin23.salbum.domain.models

import com.ichin23.salbum.data.api.dto.musicbrainz.release.ReleaseDTO
import com.ichin23.salbum.data.api.dto.salbum.rating.RatingDTO

data class AlbumDetails(
    val release: ReleaseDTO,
    val ratings: List<RatingDTO>,
    val userRating: RatingDTO?,
    val inListenList: Boolean
)
