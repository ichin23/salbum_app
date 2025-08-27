package com.ichin23.salbum.domain.repositoryImpl

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.ichin23.salbum.data.ratingsJSON
import com.ichin23.salbum.domain.models.Ratings
import com.ichin23.salbum.domain.repository.RatingsRepository
import com.ichin23.salbum.core.utils.LocalDateTimeDeserializer
import java.time.LocalDateTime

class RatingsRepositoryInMemory: RatingsRepository {
    var ratings: MutableList<Ratings> by mutableStateOf(mutableListOf())

    init{
        val gson = Gson()

        val typeTokenRating = object : TypeToken<MutableList<Ratings>>() {}.type

        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())

        val customGson = gsonBuilder.create()
        ratings = customGson.fromJson(ratingsJSON, typeTokenRating)
    }

    override fun getAllRatings(): List<Ratings> {
        return ratings
    }

    override fun getRatingsByAlbum(id: String): List<Ratings> {
        return ratings.filter { it.album.id==id }
    }

    override fun getRatingsByUser(id: String): List<Ratings> {
        return ratings.filter{it.user.id==id}
    }

    override fun addRating(rating: Ratings) {
        ratings.add(rating)
    }

}