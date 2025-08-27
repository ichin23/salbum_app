package com.ichin23.salbum.domain.repositoryImpl

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.ichin23.salbum.data.albumsJSON
import com.ichin23.salbum.domain.models.Album
import com.ichin23.salbum.domain.repository.AlbumRepository
import com.ichin23.salbum.core.utils.LocalDateTimeDeserializer
import java.time.LocalDateTime

class AlbumRepositoryInMemory: AlbumRepository {
    var albums:List<Album> by mutableStateOf<List<Album>>(arrayListOf())

    init{
        val gson = Gson()

        val typeToken = object : TypeToken<List<Album>>() {}.type

        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())

        val customGson = gsonBuilder.create()
        albums =  customGson.fromJson(albumsJSON, typeToken)
    }

    override fun getAllAlbums(): List<Album> {
        return albums;
    }

    override fun getAlbumByID(albumId: String): Album {
        return albums.first { album -> album.id == albumId }
    }

    override fun searchAlbum(query: String): List<Album> {
        return albums.filter { it.name.contains(query) }
    }
}