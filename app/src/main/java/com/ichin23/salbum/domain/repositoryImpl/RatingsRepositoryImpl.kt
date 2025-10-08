package com.ichin23.salbum.domain.repositoryImpl

import com.ichin23.salbum.data.api.ApiSalbumService
import com.ichin23.salbum.data.api.dto.salbum.rating.RatingDTO
import com.ichin23.salbum.data.api.dto.salbum.rating.RatingUserDTO
import com.ichin23.salbum.data.api.dto.salbum.rating.SendRatingDTO
import com.ichin23.salbum.domain.repository.RatingsRepository
import javax.inject.Inject

class RatingsRepositoryImpl @Inject constructor(val salbumService: ApiSalbumService): RatingsRepository {
    override fun getAllRatings(): List<RatingDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getRatingsByAlbum(id: String): List<RatingDTO> {
        return salbumService.getRatingsByAlbum(id)
    }

    override suspend fun getRatingsByUser(id: String): List<RatingUserDTO> {
        return salbumService.getRatingsByUser(id)
    }

    override suspend fun addRating(rating: SendRatingDTO): RatingDTO {
        return salbumService.createRating(rating)
    }

    override suspend fun updateRating(rating: SendRatingDTO): RatingDTO {
        return salbumService.updateRating(rating)
    }


}