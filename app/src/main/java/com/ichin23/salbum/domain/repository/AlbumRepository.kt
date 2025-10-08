package com.ichin23.salbum.domain.repository

import com.ichin23.salbum.data.api.dto.salbum.listenlist.ListenListItem
import com.ichin23.salbum.domain.models.Album
import com.ichin23.salbum.domain.models.AlbumDetails

interface AlbumRepository {
    fun getAllAlbums() : List<Album>;
    fun getAlbumByID(albumId:String): Album;
    fun searchAlbum(query:String): List<Album>;

    suspend fun getAlbumDetails(albumId: String): AlbumDetails

    suspend fun addToListenList(albumId: String)

    suspend fun removeFromListenList(albumId: String)

    suspend fun getListenList(): List<ListenListItem>
}