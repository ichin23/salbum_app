package com.ichin23.salbum.data.api.dto.salbum.rating

import com.google.gson.annotations.SerializedName
import com.ichin23.salbum.data.api.dto.salbum.utils.LinksHateoas

data class LatestOneDTO(
    @SerializedName("_embedded") val embedded: RatingsLatestOne
)
