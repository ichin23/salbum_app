package com.ichin23.salbum.domain.repository

import com.ichin23.salbum.data.api.dto.salbum.rating.RatingDTO
import com.ichin23.salbum.data.api.dto.salbum.rating.RatingUserDTO
import com.ichin23.salbum.data.api.dto.salbum.rating.SendRatingDTO

interface RatingsRepository {
    fun getAllRatings():List<RatingDTO>;
    suspend fun getRatingsByAlbum(id:String):List<RatingDTO>;
    suspend fun getRatingsByUser(id:String):List<RatingUserDTO>;
    suspend fun addRating(rating: SendRatingDTO): RatingDTO;
    suspend fun updateRating(rating: SendRatingDTO): RatingDTO
}