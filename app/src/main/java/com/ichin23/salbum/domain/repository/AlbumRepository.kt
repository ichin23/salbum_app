package com.ichin23.salbum.domain.repository

import com.ichin23.salbum.domain.models.Album

interface AlbumRepository {
    fun getAllAlbums() : List<Album>;
    fun getAlbumByID(albumId:String): Album;
    fun searchAlbum(query:String): List<Album>;
}