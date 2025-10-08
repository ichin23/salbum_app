package com.ichin23.salbum.domain.repositoryImpl

import com.ichin23.salbum.data.api.ApiSalbumService
import com.ichin23.salbum.data.api.dto.salbum.listenlist.ListenListItem
import com.ichin23.salbum.data.api.dto.salbum.listenlist.ListenListItemInputDTO
import com.ichin23.salbum.domain.models.Album
import com.ichin23.salbum.domain.models.AlbumDetails
import com.ichin23.salbum.domain.repository.AlbumRepository
import javax.inject.Inject

import java.util.UUID

class AlbumRepositoryImpl @Inject constructor(
    private val salbumService: ApiSalbumService
): AlbumRepository {
    override fun getAllAlbums(): List<Album> {
        TODO("Not yet implemented")
    }

    override fun getAlbumByID(albumId: String): Album {
        TODO("Not yet implemented")
    }

    override fun searchAlbum(query: String): List<Album> {
        TODO("Not yet implemented")
    }

    override suspend fun getAlbumDetails(albumId: String): AlbumDetails {
        val albumDetails = salbumService.getReleaseDetails(albumId)
        val listenList = salbumService.getMyListenList()
        val inListenList = listenList.any { it.item.id == albumDetails.release.id }
        return AlbumDetails(
            release = albumDetails.release,
            ratings = albumDetails.ratings,
            userRating = albumDetails.userRating,
            inListenList = inListenList
        )
    }

    override suspend fun addToListenList(albumId: String) {
        salbumService.addToMyListenList(ListenListItemInputDTO(itemId = UUID.fromString(albumId)))
    }

    override suspend fun removeFromListenList(albumId: String) {
        val listenList = salbumService.getMyListenList()
        val itemToRemove = listenList.first{it.item.id == albumId}
        salbumService.removeFromMyList(itemToRemove.id)
    }

    override suspend fun getListenList(): List<ListenListItem> {
        return salbumService.getMyListenList()
    }

}
