package com.ichin23.salbum.data.api

import com.ichin23.salbum.data.api.dto.musicbrainz.ReleaseGroupDTO
import com.ichin23.salbum.data.api.dto.musicbrainz.release.ReleaseDTO
import com.ichin23.salbum.data.api.dto.salbum.rating.RatingDTO
import com.ichin23.salbum.data.api.dto.salbum.rating.SendRatingDTO
import com.ichin23.salbum.data.api.dto.salbum.user.LoginDTO
import com.ichin23.salbum.data.api.dto.salbum.user.UserDTO
import com.ichin23.salbum.data.api.dto.salbum.user.UserLoginDTO
import com.ichin23.salbum.data.api.dto.salbum.user.UserRegisterDTO
import com.ichin23.salbum.domain.models.Ratings
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiSalbumService {

    @POST("/auth/register")
    suspend fun registerUser(@Body user: UserRegisterDTO): UserDTO

    @POST("/auth/login")
    suspend fun loginUser(@Body user: UserLoginDTO): LoginDTO

    @GET("/fetch")
    suspend fun searchAlbum(@Query("query") query: String): ReleaseGroupDTO

    @GET("/fetch/release/{id}")
    suspend fun getAlbum(@Path("id") id: String): ReleaseDTO

    @POST("/ratings/album")
    suspend fun createRating(@Body rating: SendRatingDTO): RatingDTO

    @PUT("/ratings/album")
    suspend fun updateRating(@Body rating: SendRatingDTO): RatingDTO

    @GET("/ratings/album/{id}")
    suspend fun getRatingsByAlbum(@Path("id") id: String)

    @GET("/ratings/user/{id}")
    suspend fun getRatingsByUser(@Path("id") id: String)


}