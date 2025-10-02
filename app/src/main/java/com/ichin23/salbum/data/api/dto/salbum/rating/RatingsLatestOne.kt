package com.ichin23.salbum.data.api.dto.salbum.rating

import com.google.gson.annotations.SerializedName
import com.ichin23.salbum.data.api.dto.salbum.utils.PageableDTO

data class RatingsLatestOne(
    @SerializedName("ratingOutputDTOList") val ratings: List<RatingDTO>,
    @SerializedName("_links") val links: LatestOneLinks,
    val page: PageableDTO
)
