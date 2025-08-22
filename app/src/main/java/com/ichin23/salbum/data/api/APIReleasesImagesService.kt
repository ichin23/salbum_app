package com.ichin23.salbum.data.api

import com.ichin23.salbum.data.api.dto.images.ImagesDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface APIReleasesImagesService {
    //
    @GET("release/{id}")
    suspend fun getImages(@Path("id") id:String): ImagesDTO
}