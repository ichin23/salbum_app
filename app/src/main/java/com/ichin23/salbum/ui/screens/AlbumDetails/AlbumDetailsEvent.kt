package com.ichin23.salbum.ui.screens.AlbumDetails

sealed class AlbumDetailsEvent {
    data class SendReviewAlbum(val rate: Double): AlbumDetailsEvent()
}