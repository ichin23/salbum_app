package com.ichin23.salbum.data.api

import com.ichin23.salbum.data.api.dto.musicbrainz.ReleaseGroupDTO
import com.ichin23.salbum.data.api.dto.musicbrainz.release.ReleaseDTO
import com.ichin23.salbum.data.api.dto.salbum.ReleaseDetailsDTO
import com.ichin23.salbum.data.api.dto.salbum.listenlist.ListenListItem
import com.ichin23.salbum.data.api.dto.salbum.listenlist.ListenListItemInputDTO
import com.ichin23.salbum.data.api.dto.salbum.musicmetadata.MusicMetadataCacheDTO
import com.ichin23.salbum.data.api.dto.salbum.rating.LatestOneDTO
import com.ichin23.salbum.data.api.dto.salbum.rating.RatingDTO
import com.ichin23.salbum.data.api.dto.salbum.rating.RatingUserDTO
import com.ichin23.salbum.data.api.dto.salbum.rating.SendRatingDTO
import com.ichin23.salbum.data.api.dto.salbum.user.LoginDTO
import com.ichin23.salbum.data.api.dto.salbum.user.RefreshTokenOut
import com.ichin23.salbum.data.api.dto.salbum.user.UserDTO
import com.ichin23.salbum.data.api.dto.salbum.user.UserLoginDTO
import com.ichin23.salbum.data.api.dto.salbum.user.UserRegisterDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
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

    @POST("/auth/refreshToken")
    suspend fun refreshToken(@Body refreshToken: RefreshTokenOut): LoginDTO

    @GET("/users/me")
    suspend fun getMe(): UserDTO

    @GET("/search")
    suspend fun search(@Query("q") q: String): List<MusicMetadataCacheDTO>

    @GET("/fetch")
    suspend fun searchAlbum(@Query("query") query: String): ReleaseGroupDTO

    @GET("/fetch/release/{id}")
    suspend fun getAlbum(@Path("id") id: String): ReleaseDTO

    @GET("/fetch/releaseDetails/{id}")
    suspend fun getReleaseDetails(@Path("id") id: String): ReleaseDetailsDTO

    @GET("/ratings/latestOne")
    suspend fun getLatestOne(): LatestOneDTO

    @POST("/ratings/album")
    suspend fun createRating(@Body rating: SendRatingDTO): RatingDTO

    @PUT("/ratings/album")
    suspend fun updateRating(@Body rating: SendRatingDTO): RatingDTO

    @GET("/ratings/album/{id}")
    suspend fun getRatingsByAlbum(@Path("id") id: String): List<RatingDTO>

    @GET("/ratings/user/{id}")
    suspend fun getRatingsByUser(@Path("id") id: String): List<RatingUserDTO>

    @GET("/listenlist")
    suspend fun getMyListenList(): List<ListenListItem>

    @POST("/listenlist")
    suspend fun addToMyListenList(@Body listenListItem: ListenListItemInputDTO): ListenListItem

    @DELETE("/listenlist/{id}")
    suspend fun removeFromMyList(@Path("id") id: String)
}