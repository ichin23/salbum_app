package com.ichin23.salbum.data.api

import com.ichin23.salbum.data.api.dto.musicbrainz.release.ReleaseDTO
import com.ichin23.salbum.data.api.dto.musicbrainz.ReleaseGroupDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIMusicBrainzService {
    @GET("release-group")
    suspend fun searchAlbum(
        @Query("query") query:String,
        @Query("limit") limit:Int = 3,
        @Query("fmt") fmt:String = "json"): ReleaseGroupDTO

    @GET("release/{albumId}")
    suspend fun lookupAlbum(
        @Path("albumId") albumId: String,
        @Query("inc") inc:String = "url-rels+artists+recordings",
        @Query("fmt") fmt:String = "json"
        ) : ReleaseDTO
}