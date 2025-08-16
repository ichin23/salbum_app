package com.ichin23.salbum.domain.repository

import com.ichin23.salbum.domain.models.Ratings

interface RatingsRepository {
    fun getAllRatings():List<Ratings>;
    fun getRatingsByAlbum(id:String):List<Ratings>;
    fun getRatingsByUser(id:String):List<Ratings>;
    fun addRating(rating: Ratings);
}