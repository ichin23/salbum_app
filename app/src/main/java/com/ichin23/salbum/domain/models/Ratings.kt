package com.ichin23.salbum.domain.models

import com.google.gson.annotations.SerializedName
import com.ichin23.salbum.data.api.dto.salbum.rating.RatingDTO
import java.time.LocalDateTime

data class Ratings(
    val rate: Double,
    @SerializedName("updated_at")
    val updatedAt: LocalDateTime,
    @SerializedName("created_at")
    val createdAt: LocalDateTime,
    val user: User,
    val album: Album
){

    //constructor (ratingDto: RatingDTO): this(ratingDto.rate, ratingDto.created_at, ratingDto.updated_at, ratingDto.user, ratingDto.album)
}
